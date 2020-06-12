package com.codebrewers.onlinebookstore.utils;

import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IMailService {


    String sendMail(String body,String subject,String emailID) throws MessagingException, IOException;
}
