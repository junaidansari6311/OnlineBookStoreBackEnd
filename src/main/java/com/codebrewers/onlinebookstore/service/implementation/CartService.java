package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.BookCartDetails;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.repository.IBookCartDetailsRepository;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.repository.ICartRepository;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.ICartService;
import com.codebrewers.onlinebookstore.utils.IToken;
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

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    IToken jwtToken;

    @Autowired
    private IUserRepository userRepository;



    @Override
    public String addToCart(CartDTO cartDTO, String token) {
        int userId = jwtToken.decodeJWT(token);
        UserDetails user = userRepository.findById(userId).orElseThrow(()->new UserServiceException("User Not Found"));
        CartDetails cartDetails = cartRepository.findByUserDetails(user)
                .orElseThrow(() -> new CartException("Cart Not Found"));
        BookCartDetails bookCartDetails = new BookCartDetails(cartDTO);
        BookDetails books = bookStoreRepository.findById(cartDTO.id).get();
        List<BookCartDetails> cartList = new ArrayList<>();
        cartList.add(bookCartDetails);
        cartDetails.getBook().add(bookCartDetails);
        cartDetails.setBook(cartList);
        cartRepository.save(cartDetails);
        bookCartDetails.setCartDetails(cartDetails);
        bookCartDetails.setBookDetails(books);
        bookCartDetailsRepository.save(bookCartDetails);
        return "Book Added To Cart Successfully";
    }

    @Override
    public List<BookCartDetails> allCartItems(String token) {
        int userId = jwtToken.decodeJWT(token);
        UserDetails user = userRepository.findById(userId).orElseThrow(()-> new UserServiceException("User Not Found"));
        CartDetails cartDetails = cartRepository.findByUserDetails(user).orElseThrow(()->new CartException("Cart Not Found"));
        List<BookCartDetails> bookCartDetails = bookCartDetailsRepository.fetchCartItems(cartDetails.getId());
        return bookCartDetails;
    }

    @Override
    public String updateQuantityAndPrice(CartDTO cartDTO, String token) {
        int verifyToken = jwtToken.decodeJWT(token);
        userRepository.findById(verifyToken);

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

    @Override
    public CartDetails setCart(UserDetails userDetails) {
        CartDetails cartDetails = new CartDetails();
        cartDetails.setUserDetails(userDetails);
        cartRepository.save(cartDetails);
        return cartDetails;
    }

}
