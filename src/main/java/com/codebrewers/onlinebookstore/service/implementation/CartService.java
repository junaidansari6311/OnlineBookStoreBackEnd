package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.exception.BookStoreException;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.repository.IcartRepository;
import com.codebrewers.onlinebookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private IcartRepository icartRepository;

    @Override
    public String addTOCart(CartDTO cartDTO) {
        CartDetails cartDetails = new CartDetails(cartDTO);
        Optional<CartDetails> byBookName = icartRepository.findByBookName(cartDTO.bookName);
        if(byBookName.isPresent()){
            throw new CartException("Book Already Present");
        }
        icartRepository.save(cartDetails);
        return "book addded";
    }

    @Override
    public List<CartDetails> allCartItems() {
        List<CartDetails> all = icartRepository.findAll();
        if(all.isEmpty()){
            throw new CartException("No Books Available");
        }
        return all;
    }

    @Override
    public String UpdateQuantity(CartDTO cartDTO) {
        Optional<CartDetails> byBookID = icartRepository.findByBookID(cartDTO.bookID);
        if(byBookID.isPresent()){
            CartDetails cartDetails = byBookID.get();
            cartDetails.setQuantity(cartDTO.quantity);
            icartRepository.save(cartDetails);
            return "Book Quantity Update";
        }
        throw new BookStoreException("No Books Available");
    }
}
