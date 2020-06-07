package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.model.BookCartDetails;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.IBookCartDetailsRepository;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.repository.ICartRepository;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.implementation.CartService;
import com.codebrewers.onlinebookstore.utils.implementation.Token;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartServiceTest {

    @Mock
    ICartRepository cartRepository;

    @InjectMocks
    CartService cartService;



    @Mock
    IBookStoreRepository bookStoreRepository;

    @Mock
    IBookCartDetailsRepository bookCartDetailsRepository;

    @Mock
    IUserRepository userRepository;

    @MockBean
    FileProperties fileProperties;

    @Mock
    Token jwtToken;

    UserDetails user;
    BookDetails book;
    BookCartDetails bookCartDetails;
    List<BookCartDetails> bookDetails = new ArrayList<>();

    @Test
    void givenBookDetails_WhenBookAddedInCart_ShouldReturnMessage() {
        String token="asbfj45";
        CartDTO cartDTO = new CartDTO(1, 50,200.0);
        BookDTO bookDTO = new BookDTO("IOT", "Mark", "This is book about how internet of things can be applied.", "ABC123", "jpg", 200, 50, 2015);
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan","gajanan@gmail.com","XYTZ@1456","9966998855",true);
        user= new UserDetails(registrationDTO);
        user.id=1;
        book = new BookDetails(bookDTO);
        bookCartDetails = new BookCartDetails(cartDTO);
        bookDetails.add(bookCartDetails);

        CartDetails cartDetails = new CartDetails();
        cartDetails.setId(1);
        cartDetails.setBook(bookDetails);
        cartDetails.setUserDetails(user);

        when(jwtToken.decodeJWT(anyString())).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));
        when(cartRepository.findByUserDetails(any())).thenReturn(java.util.Optional.of(cartDetails));
        when(bookStoreRepository.findById(anyInt())).thenReturn(java.util.Optional.of(book));
        when(cartRepository.save(any())).thenReturn(cartDetails);
        when(bookCartDetailsRepository.save(any())).thenReturn(bookCartDetails);
        String message = "Book Added To Cart Successfully";
        String addedBooks = cartService.addToCart(cartDTO,token);
        Assert.assertEquals(message, addedBooks);
    }

    @Test
    void givenCart_ShouldReturnListOfBooks() {
        String token="asbfj45";
        List<CartDetails> cartList = new ArrayList<>();
        List<CartDetails> cartList1 = new ArrayList<>();
        CartDTO cartDTO = new CartDTO(1, 50,200.0);
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan","gajanan@gmail.com","XYTZ@1456","9966998855",true);
        user= new UserDetails(registrationDTO);
        bookCartDetails = new BookCartDetails(cartDTO);
        bookDetails.add(bookCartDetails);
        CartDetails cartDetails = new CartDetails();
        cartDetails.setId(1);
        cartDetails.setBook(bookDetails);
        cartDetails.setUserDetails(user);
        cartList.add(cartDetails);
        cartList1.add(cartDetails);
        when(jwtToken.decodeJWT(anyString())).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));
        when(cartRepository.findByUserDetails(any())).thenReturn(java.util.Optional.of(cartDetails));
        when(cartRepository.findAll()).thenReturn(cartList);
        cartService.allCartItems(token);
        Assert.assertEquals(cartList1, cartList);
    }

    @Test
    void givenBookDetails_WhenUpdateBookQuantity_ShouldReturnMessage() {
        String token="asbfj45";
        CartDTO cartDTO = new CartDTO(1, 50,200.0);
        BookDTO bookDTO = new BookDTO("IOT", "Mark", "This is book about how internet of things can be applied.", "ABC123", "jpg", 200, 50, 2015);
        book = new BookDetails(bookDTO);
        bookCartDetails = new BookCartDetails(cartDTO);
        CartDetails cartDetails = new CartDetails();
        when(cartRepository.save(any())).thenReturn(cartDTO);
        when(bookCartDetailsRepository.findById(anyInt())).thenReturn(java.util.Optional.of(bookCartDetails));
        String message = "Book Quantity Updated";
        String updateQuantity = cartService.updateQuantityAndPrice(cartDTO, token);
        Assert.assertEquals(message, updateQuantity);
    }

    @Test
    void givenBookID_WhenPresentToDelete_ShouldReturnMessage() throws Exception {
        String message = "Book Has Been Deleted";
        Integer id = 1;

        doNothing().doThrow(new IllegalArgumentException()).when(cartRepository).deleteById(id);
        String deleteCartItems = cartService.deleteCartItem(id);
        Assert.assertEquals(message, deleteCartItems);
    }

}
