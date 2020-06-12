package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.IUserService;
import com.codebrewers.onlinebookstore.utils.IToken;
import com.codebrewers.onlinebookstore.utils.implementation.MailService;
import com.codebrewers.onlinebookstore.utils.templet.EmailVerification;
import com.codebrewers.onlinebookstore.utils.templet.ForgotPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    IToken jwtToken;

    @Autowired
    EmailVerification emailVerification;

    @Autowired
    MailService mailService;

    @Autowired
    private CartService cartService;

    @Autowired
    ForgotPassword forgotPassword;

    @Override
    public String userRegistration(RegistrationDTO registrationDTO,String requestURL) throws MessagingException {
        Optional<UserDetails> userDetail = userRepository.findByEmailID(registrationDTO.emailID);

        if (userDetail.isPresent()) {
            throw new UserServiceException("USER ALREADY EXISTS WITH THIS EMAIL ID");
        }

        String password = bCryptPasswordEncoder.encode(registrationDTO.password);
        UserDetails userDetails = new UserDetails(registrationDTO);
        userDetails.password = password;
        userRepository.save(userDetails);
        cartService.setCart(userDetails);
        sendVerificationMail(registrationDTO.emailID, requestURL);
        return "Verification Mail Has Been Sent Successfully";
    }

    @Override
    public String userLogin(LoginDTO loginDTO) {
        Optional<UserDetails> userDetail = userRepository.findByEmailID(loginDTO.emailID);

        if (userDetail.isPresent()) {
            if(userDetail.get().isVerified){
                boolean password = bCryptPasswordEncoder.matches(loginDTO.password, userDetail.get().password);
                if (password) {
                    String tokenString = jwtToken.generateLoginToken(userDetail.get());
                    return tokenString;
                }
                throw new UserServiceException("INCORRECT PASSWORD");
            }
            throw new UserServiceException("Please verify your email before proceeding");
        }
        throw new UserServiceException("INCORRECT EMAIL");
    }

    @Override
    public String resetPasswordLink(String email, String urlToken) throws MessagingException {
        UserDetails user = userRepository.findByEmailID(email).orElseThrow(() -> new UserServiceException("User Not Found"));
        String tokenGenerate = jwtToken.generateVerificationToken(user);
        urlToken = forgotPassword.getHeader(urlToken.contains("forgot")?
                urlToken.substring(0, urlToken.indexOf("f") - 1) + "/reset/password/" + tokenGenerate :
                urlToken.substring(0, urlToken.indexOf("r") - 1) + "/reset/password/" + tokenGenerate );
        mailService.sendMail(urlToken, "Reset Password", user.emailID);
        return "Reset Password Link Has Been Sent To Your Email Address";
    }

    @Override
    public String resetPassword(String password, String urlToken) {
        int userId = jwtToken.decodeJWT(urlToken);
        UserDetails userDetails = userRepository.findById(userId).orElseThrow(() -> new UserServiceException("User Not Found"));
        String encodePassword = bCryptPasswordEncoder.encode(password);
        userDetails.password = encodePassword;
        userRepository.save(userDetails);
        return "Password Has Been Reset";
    }

    @Override
    public String sendVerificationMail(String email, String requestURL) throws MessagingException {
        UserDetails user = userRepository.findByEmailID(email).orElseThrow(()->new UserServiceException("User Not Found"));
        String token = jwtToken.generateVerificationToken(user);
        requestURL= emailVerification.getHeader(requestURL.contains("user") ?
                requestURL.substring(0, requestURL.indexOf("u") - 1) + "/verify/email/" + token  :
                requestURL.contains("resend") ?
                        requestURL.substring(0, requestURL.indexOf("r") - 1) + "/verify/email/" + token :
                        requestURL + "verify/email/" + token);
        String subject="Email Verification";
        mailService.sendMail(requestURL,subject,user.emailID);
        return "Verification Mail Has Been Sent Successfully";
    }


    @Override
    public String verifyEmail(String token) {
        int userId = jwtToken.decodeJWT(token);
        UserDetails user = userRepository.findById(userId).get();
        user.isVerified=true;
        userRepository.save(user);
        return "User Has Been Verified";
    }

}
