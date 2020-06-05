package com.codebrewers.onlinebookstore.service;

import javax.mail.MessagingException;

public interface IOrderService {

    Integer placeOrder(Double totalPrice, String token) throws MessagingException;

}
