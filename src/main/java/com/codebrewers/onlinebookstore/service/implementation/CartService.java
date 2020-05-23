package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.OrderBookDetailsDTO;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.repository.ICartRepository;
import com.codebrewers.onlinebookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailService mailService;

    @Autowired
    private ICartRepository icartRepository;


    @Override
    public String addToCart(CartDTO cartDTO) {
        CartDetails cartDetails = new CartDetails(cartDTO);
        Optional<CartDetails> byBookName = icartRepository.findByBookName(cartDTO.bookName);
        if (byBookName.isPresent()) {
            throw new CartException("Book Already Present");
        }
        icartRepository.save(cartDetails);
        return "book addded";
    }

    @Override
    public List<CartDetails> allCartItems() {
        List<CartDetails> all = icartRepository.findAll();
        if (all.isEmpty()) {
            throw new CartException("No Books Available");
        }
        return all;
    }

    @Override
    public String updateQuantity(CartDTO cartDTO) {
        Optional<CartDetails> byBookID = icartRepository.findByBookID(cartDTO.bookID);
        if (byBookID.isPresent()) {
            CartDetails cartDetails = byBookID.get();
            cartDetails.setQuantity(cartDTO.quantity);
            icartRepository.save(cartDetails);
            return "Book Quantity Update";
        }
        throw new CartException("No Books Available");
    }

    @Override
    public String deleteCartItem(Integer id) {
        icartRepository.deleteById(id);
        return "Cart Has Been Deleted";
    }

    @Override
    public void sendMail(OrderBookDetailsDTO order) throws MessagingException {
        mailService.sendMail(order);
    }
}
