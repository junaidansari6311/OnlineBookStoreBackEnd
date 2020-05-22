package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.model.CartDetails;

import java.util.List;

public interface ICartService {


    String addToCart(CartDTO cartDTO);

    List<CartDetails> allCartItems();

    String updateQuantity(CartDTO cartDTO);

    String deleteCartItem(Integer id);
}