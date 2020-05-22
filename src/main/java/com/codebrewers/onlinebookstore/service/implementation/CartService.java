package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.repository.ICartRepository;
import com.codebrewers.onlinebookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private ICartRepository cartRepository;

    @Override
    public String addToCart(CartDTO cartDTO) {
        CartDetails cartDetails = new CartDetails(cartDTO);
        Optional<CartDetails> byBookName = cartRepository.findByBookName(cartDTO.bookName);
        if (byBookName.isPresent()) {
            throw new CartException("Book Already Present");
        }
        cartRepository.save(cartDetails);
        return "Book Added Successfully";
    }

    @Override
    public List<CartDetails> allCartItems() {
        List<CartDetails> all = cartRepository.findAll();
        if (all.isEmpty()) {
            throw new CartException("No Books Available");
        }
        return all;
    }

    @Override
    public String updateQuantity(CartDTO cartDTO) {
        Optional<CartDetails> byBookID = cartRepository.findByBookID(cartDTO.bookID);
        if (byBookID.isPresent()) {
            CartDetails cartDetails = byBookID.get();
            cartDetails.quantity=cartDTO.quantity;
            cartRepository.save(cartDetails);
            return "Book Quantity Updated";
        }
        throw new CartException("No Books Available");
    }

    @Override
    public String deleteCartItem(Integer id) {
        cartRepository.deleteById(id);
        return "Book Has Been Deleted";
    }
}
