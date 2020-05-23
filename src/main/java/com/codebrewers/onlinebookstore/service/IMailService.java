package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IMailService {


    void sendMail(CustomerDetailsDTO customerDetailsDTO) throws MessagingException, IOException;
}
