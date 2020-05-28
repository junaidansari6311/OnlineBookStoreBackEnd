package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.model.BookCartDetails;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.repository.IBookCartDetailsRepository;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.repository.ICartRepository;
import com.codebrewers.onlinebookstore.service.ICartService;
import com.codebrewers.onlinebookstore.utils.implementation.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailService mailService;

    @Autowired
    private ICartRepository icartRepository;

    @Autowired
    private IBookStoreRepository bookStoreRepository;

    @Autowired
    private IBookCartDetailsRepository bookCartDetailsRepository;


    @Override
    public String addToCart(CartDTO cartDTO) {

        BookCartDetails bookCartDetails = new BookCartDetails(cartDTO);
        BookDetails books = bookStoreRepository.findById(cartDTO.id).get();
        List<BookCartDetails> cartList = new ArrayList<>();
        cartList.add(bookCartDetails);
        CartDetails cartDetails = new CartDetails(1,cartList);
        icartRepository.save(cartDetails);
        bookCartDetails.setCartDetails(cartDetails);
        bookCartDetails.setBookDetails(books);
        System.out.println(bookCartDetails);
        bookCartDetailsRepository.save(bookCartDetails);
        return "Book Added To Cart Successfully";
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
    public String updateQuantityAndPrice(CartDTO cartDTO) {
        BookCartDetails bookCartDetails = bookCartDetailsRepository.findById(cartDTO.id).get();
        bookCartDetails.setQuantity(cartDTO.quantity);
        bookCartDetails.setTotalPrice(cartDTO.totalPrice);
        bookCartDetailsRepository.save(bookCartDetails);
        return "Book Quantity Updated";
    }

    @Override
    public String deleteCartItem(Integer id) {
        icartRepository.deleteById(id);
        return "Cart Has Been Deleted";
    }

    @Override
    public void sendMail(CustomerDetailsDTO order) throws MessagingException {
        mailService.sendMail(order);
    }
}
