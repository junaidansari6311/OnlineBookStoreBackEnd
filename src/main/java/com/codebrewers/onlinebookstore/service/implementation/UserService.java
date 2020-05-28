package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.IUserService;
import com.codebrewers.onlinebookstore.utils.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    IToken jwtToken;

    @Override
    public String userRegistration(RegistrationDTO registrationDTO) {
        boolean byEmail = userRepository.findByEmailID(registrationDTO.emailID).isPresent();
        if (byEmail) {
            throw new UserServiceException("User Already Exists");
        }
        String password = bCryptPasswordEncoder.encode(registrationDTO.password);
        UserDetails userDetails = new UserDetails(registrationDTO);
        userDetails.password = password;
        userRepository.save(userDetails);
        return "REGISTRATION SUCCESSFUL";
    }

    @Override
    public String userLogin(LoginDTO loginDTO) {
        Optional<UserDetails> userDetail = userRepository.findByEmailID(loginDTO.emailID);

        if (userDetail.isPresent()) {
            boolean password = bCryptPasswordEncoder.matches(loginDTO.password, userDetail.get().password);
            if (password) {
                String tokenString = jwtToken.generateLoginToken(userDetail.get());
                return tokenString;
            }
            throw new UserServiceException("INCORRECT PASSWORD");
        }
        throw new UserServiceException("INCORRECT EMAIL");
    }

}
