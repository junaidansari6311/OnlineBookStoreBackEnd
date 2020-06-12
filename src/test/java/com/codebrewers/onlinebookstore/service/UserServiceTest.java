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

import javax.mail.MessagingException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Mock
    Token jwtToken;

    @MockBean
    FileProperties fileProperties;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void givenUserDetails_WhenUserAlreadyPresent_ShouldThrowException() {
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan", "gajanan@gmail.com", "Gajanan@123", "8855885588", true);
        UserDetails userDetails = new UserDetails(registrationDTO);
        String message = "USER ALREADY EXISTS WITH THIS EMAIL ID";
        try {
            when(userRepository.findByEmailID(any())).thenReturn(java.util.Optional.of(userDetails));
            when(userRepository.save(any())).thenReturn(message);
            userService.userRegistration(registrationDTO, "url");
        } catch (UserServiceException | MessagingException e) {
            Assert.assertEquals(message, e.getMessage());
        }
    }

    @Test
    void givenUserLogin_WhenInvalidPassword_ShouldThrowException() {
        LoginDTO loginDTO = new LoginDTO("pritam@gmail.com", "pritam123");
        UserDetails userDetails = new UserDetails(loginDTO);
        String message = "Please verify your email before proceeding";
        try {
            when(userRepository.findByEmailID(loginDTO.emailID)).thenReturn(java.util.Optional.of(userDetails));
            when(bCryptPasswordEncoder.matches(loginDTO.password, userDetails.password)).thenReturn(true);
            userService.userLogin(loginDTO);
        } catch (UserServiceException e) {
            Assert.assertEquals(message, e.getMessage());
        }
    }

    @Test
    void givenUserLogin_WhenInvalidEmailID_ShouldThrowException() {
        LoginDTO loginDTO = new LoginDTO("pritam@gmail.com", "pritam123");
        UserDetails userDetails = new UserDetails(loginDTO);
        String message = "INCORRECT EMAIL";
        try {
            when(bCryptPasswordEncoder.matches(loginDTO.password, userDetails.password)).thenReturn(Boolean.valueOf(message));
            userService.userLogin(loginDTO);
        } catch (UserServiceException e) {
            Assert.assertEquals(message, e.getMessage());
        }
    }

    @Test
    void givenUserDetails_WhenUserVerifyEmail_ShouldReturnMessage() {
        String token="ghfd12hvw";
        String message = "User Has Been Verified";
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan", "gajanan@gmail.com", "Gajanan@123", "8855885588",true);
        UserDetails userDetails = new UserDetails(registrationDTO);

        when(jwtToken.decodeJWT(anyString())).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(userDetails));
        when(userRepository.save(any())).thenReturn(userDetails);
        String verifyEmail = userService.verifyEmail(token);
        Assert.assertEquals(message,verifyEmail);
    }

    @Test
    void givenUserDetails_WhenUserSetThePassword_ShouldReturnMessage() {
        String password="Gajanan@123";
        String token="ghfd12hvw";
        String message = "Password Has Been Reset";
        LoginDTO loginDTO = new LoginDTO("pritam@gmail.com","pritam123");
        UserDetails userDetails = new UserDetails(loginDTO);

        when(jwtToken.decodeJWT(anyString())).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(userDetails));
        when(bCryptPasswordEncoder.encode(password)).thenReturn(password);
        when(userRepository.save(any())).thenReturn(userDetails);
        String reset = userService.resetPassword(password,token);
        Assert.assertEquals(message,reset);
    }

}