package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public String userRegistration(RegistrationDTO registrationDTO) {
        UserDetails userDetails = new UserDetails(registrationDTO);
        boolean byEmail = userRepository.findByEmailID(registrationDTO.emailID).isPresent();
        if (byEmail) {
            throw new UserServiceException("User Already Exists");
        }
        userRepository.save(userDetails);
        return "REGISTRATION SUCCESSFUL";
    }
}
