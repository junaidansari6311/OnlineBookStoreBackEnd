package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.IUserService;
import com.codebrewers.onlinebookstore.utils.IToken;
import com.codebrewers.onlinebookstore.utils.implementation.MailService;
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
    MailService mailService;

    @Autowired
    private CartService cartService;

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
        System.out.println("email: "+loginDTO.emailID);
        Optional<UserDetails> userDetail = userRepository.findByEmailID(loginDTO.emailID);

        if (userDetail.isPresent()) {
            if(userDetail.get().isVerified){
                boolean password = bCryptPasswordEncoder.matches(loginDTO.password, userDetail.get().password);
                if (password) {
                    String tokenString = jwtToken.generateLoginToken(userDetail.get());
                    System.out.println(tokenString);
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
        return null;
    }

    @Override
    public String sendVerificationMail(String email, String requestURL) throws MessagingException {
        UserDetails user = userRepository.findByEmailID(email).orElseThrow(()->new UserServiceException("User Not Found"));
        String token = jwtToken.generateVerificationToken(user);
        requestURL= requestURL.contains("user") ?
                requestURL.substring(0, requestURL.indexOf("u") - 1) + "verify/email/" + token  :
                requestURL.contains("resend") ?
                        requestURL.substring(0, requestURL.indexOf("r") - 1) + "verify/email/" + token :
                        requestURL + "verify/email/" + token;
        String subject="Email Verification";
        mailService.sendMail(requestURL,subject,user.emailID);
        return "Verification Mail Has Been Sent Successfully";
    }


    @Override
    public String verifyEmail(String token) {
        System.out.println("verify mail: "+token);
        int userId = jwtToken.decodeJWT(token);
        UserDetails user = userRepository.findById(userId).get();
        user.isVerified=true;
        userRepository.save(user);
        return "User Has Been Verified";
    }

}
