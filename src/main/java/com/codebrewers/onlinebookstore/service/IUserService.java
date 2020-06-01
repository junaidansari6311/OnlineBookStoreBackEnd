package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;

import javax.mail.MessagingException;

public interface IUserService {

    String userRegistration(RegistrationDTO registrationDTO,String requestURL) throws MessagingException;

    String userLogin(LoginDTO logInDTO);

    String sendVerificationMail(String email, String requestURL) throws MessagingException;

    String verifyEmail(String token);

    String resetPasswordLink(String email, String urlToken) throws MessagingException;

}
