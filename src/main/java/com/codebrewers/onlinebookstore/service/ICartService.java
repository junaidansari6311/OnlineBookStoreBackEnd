package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.MailDTO;
import com.codebrewers.onlinebookstore.model.CartDetails;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.List;

public interface ICartService {


    String addToCart(CartDTO cartDTO);

    List<CartDetails> allCartItems();

    String updateQuantity(CartDTO cartDTO);

    String deleteCartItem(Integer id);

    void sendMail(MailDTO mailDTO) throws IOException, MessagingException;
}