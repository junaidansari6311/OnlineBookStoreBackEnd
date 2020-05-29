package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.model.BookCartDetails;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;

import javax.mail.MessagingException;
import java.util.List;

public interface ICartService {


    String addToCart(CartDTO cartDTO, String token);

    List<BookCartDetails> allCartItems(String token);

    String updateQuantityAndPrice(CartDTO cartDTO);

    String deleteCartItem(Integer id);

    void sendMail(CustomerDetailsDTO order) throws MessagingException;

    CartDetails setCart(UserDetails userDetails);

}