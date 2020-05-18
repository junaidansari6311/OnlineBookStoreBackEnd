package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.repository.IcartRepository;
import com.codebrewers.onlinebookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
