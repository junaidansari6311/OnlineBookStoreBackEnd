package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.implementation.UserService;
import com.codebrewers.onlinebookstore.utils.implementation.Token;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    UserService userService;

    @MockBean
    FileProperties fileProperties;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    Token jwtToken;


    @Test
    void givenUserDetails_WhenRegistered_ShouldReturnMessage() {
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan", "gajanan@gmail.com", "Gajanan@123", "8855885588");
        UserDetails userDetails = new UserDetails(registrationDTO);
        String message = "USER ALREADY EXISTS WITH THIS EMAIL ID";
        try {
            when(userRepository.findByEmailID(any())).thenReturn(java.util.Optional.of(userDetails));
            when(userRepository.save(any())).thenReturn(message);
            userService.userRegistration(registrationDTO);
        }catch(UserServiceException e){
            Assert.assertEquals(message, e.getMessage());
        }

    }

    @Test
    void givenUserDetails_WhenUserLogedin_ShouldReturnMessage() {
        LoginDTO loginDTO = new LoginDTO("gajanan@gmail.com", "Gajanan@123");
        UserDetails userDetails = new UserDetails(loginDTO);
        String message = "token";
        when(userRepository.findByEmailID(any())).thenReturn(java.util.Optional.of(userDetails));
        when(bCryptPasswordEncoder.matches(loginDTO.password,userDetails.password)).thenReturn(true);
        when(jwtToken.generateLoginToken(userDetails)).thenReturn("token");
        String user = userService.userLogin(loginDTO);
        Assert.assertEquals(message, user);
    }
}
