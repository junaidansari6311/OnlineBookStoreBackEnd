package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.model.BookCartDetails;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;
import java.util.List;

public interface ICartService {


    String addToCart(CartDTO cartDTO, String token);

    List<BookCartDetails> allCartItems(String token);

    String updateQuantityAndPrice(CartDTO cartDTO, String token);

    String deleteCartItem(Integer id);

    CartDetails setCart(UserDetails userDetails);

}