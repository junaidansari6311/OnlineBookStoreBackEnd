package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.OrderBookDetailsDTO;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IMailService {


    void sendMail(OrderBookDetailsDTO orderBookDetailsDTO) throws MessagingException, IOException;
}
