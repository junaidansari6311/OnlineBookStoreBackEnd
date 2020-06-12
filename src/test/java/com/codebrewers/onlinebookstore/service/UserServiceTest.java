package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.exception.JWTException;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.implementation.UserService;
import com.codebrewers.onlinebookstore.utils.implementation.MailService;
import com.codebrewers.onlinebookstore.utils.implementation.Token;
import com.codebrewers.onlinebookstore.utils.templet.EmailVerification;
import com.codebrewers.onlinebookstore.utils.templet.ForgotPassword;
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
    ForgotPassword forgotPassword;

    @Mock
    EmailVerification emailVerification;

    @Mock
    MailService mailService;

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

    @Test
    void givenUserDetailsToLoginUser_WhenIncorrectPasswordEntered_ShouldThrowException() {
        LoginDTO loginDTO = new LoginDTO("gajanan@gmail.com","Gajanan@123");
        UserDetails userDetails = new UserDetails(loginDTO);
        userDetails.isVerified = true;
        try {

            when(userRepository.findByEmailID(loginDTO.emailID)).thenReturn(java.util.Optional.of(userDetails));
            when(bCryptPasswordEncoder.matches(loginDTO.password, userDetails.password)).thenReturn(false);
            userService.userLogin(loginDTO);
        } catch (UserServiceException ex) {
            Assert.assertEquals("INCORRECT PASSWORD", ex.getMessage());
        }
    }

    @Test
    void givenUserDetailsToLoginUser_WhenIncorrectEmailEntered_ShouldThrowException() {
        LoginDTO loginDTO = new LoginDTO("gajanan@gmail.com","Gajanan123");
        try {
            when(userRepository.findByEmailID(loginDTO.emailID)).thenThrow(new UserServiceException("Enter Registered Email"));
            userService.userLogin(loginDTO);
        } catch (UserServiceException e) {
            Assert.assertEquals("Enter Registered Email", e.getMessage());
        }
    }

    @Test
    void givenRequestToVerifyUser_WhenUserAlreadyVerifiedWithSameEmail_ShouldThrowException() {
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan", "gajanan@gmail.com", "Gajanan@123", "8855885588", true);
        UserDetails userDetails = new UserDetails(registrationDTO);
        try {
            when(jwtToken.generateVerificationToken(any())).thenReturn(String.valueOf(userDetails));
            when(userRepository.findById(anyInt())).thenThrow(new JWTException("User With Same Email Id Already Exists"));
            when(userRepository.save(any())).thenReturn(userDetails);
            userService.verifyEmail("authorization");
        } catch (JWTException e) {
            Assert.assertEquals("User With Same Email Id Already Exists", e.getMessage());
        }
    }

    @Test
    void givenUserDetails_WhenUserResetPassword_ShouldReturnResetPasswordLinkMessage() throws MessagingException {
        LoginDTO loginDTO = new LoginDTO("pritam@gmail.com","pritam123");
        UserDetails userDetails = new UserDetails(loginDTO);
        when(userRepository.findByEmailID(loginDTO.emailID)).thenReturn(java.util.Optional.of(userDetails));
        when(jwtToken.generateVerificationToken(any())).thenReturn(String.valueOf(userDetails));
        when(forgotPassword.getHeader(anyString())).thenReturn("token");
        when(mailService.sendMail(any(),any(),any())).thenReturn("Mail has been send");
        String user = userService.resetPasswordLink("pritam@gmail.com", "tokenforgot");
        Assert.assertEquals("Reset Password Link Has Been Sent To Your Email Address",user);
    }

    @Test
    void givenUserDetails_WhenUserRegistered_ShouldReturnVerificationMessage() throws MessagingException {
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan", "gajanan@gmail.com", "Gajanan@123", "8855885588", true);
        UserDetails userDetails = new UserDetails(registrationDTO);
        when(jwtToken.generateVerificationToken(any())).thenReturn(String.valueOf(userDetails));
        when(userRepository.findByEmailID(registrationDTO.emailID)).thenReturn(java.util.Optional.of(userDetails));
        when(bCryptPasswordEncoder.matches(registrationDTO.password, userDetails.password)).thenReturn(false);
        when(userRepository.save(any())).thenReturn(userDetails);
        when(emailVerification.getHeader(anyString())).thenReturn("token");
        when(mailService.sendMail(any(),any(),any())).thenReturn("Mail has been send");
        String user = userService.sendVerificationMail("gajanan@gmail.com", "tokenuser");
        Assert.assertEquals("Verification Mail Has Been Sent Successfully",user);
    }
}