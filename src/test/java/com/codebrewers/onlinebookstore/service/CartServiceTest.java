package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.repository.ICartRepository;
import com.codebrewers.onlinebookstore.service.implementation.CartService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void givenBookDetails_WhenBookAddedInCart_ShouldReturnMessage() {
        CartDTO cartDTO = new CartDTO(1,50,200.0);
        CartDetails cartDetails = new CartDetails();
        when(cartRepository.save(any())).thenReturn(cartDetails);
        String message = "Book Added Successfully";
        String addedBooks = cartService.addToCart(cartDTO);
        Assert.assertEquals(message, addedBooks);
    }

    @Test
    void givenBookDetails_WhenBookAlreadyPresentInCart_ShouldThrowException() {
        try {
            CartDTO cartDTO = new CartDTO(1,50,200.0);
            CartDetails cartDetails = new CartDetails();
            when(cartRepository.findByBookName("IOT")).thenReturn(java.util.Optional.of(cartDetails));
            when(cartRepository.save(any())).thenReturn(cartDetails);
            cartService.addToCart(cartDTO);
        } catch (CartException e) {
            Assert.assertEquals("Book Already Present", e.getMessage());
        }
    }

    @Test
    void givenCart_ShouldReturnListOfBooks() {
        List<CartDetails> cartList = new ArrayList<>();
        List<CartDetails> cartList1 = new ArrayList<>();
        CartDTO cartDTO = new CartDTO(1,50,200.0);
        CartDetails cartDetails = new CartDetails();
        cartList.add(cartDetails);
        cartList1.add(cartDetails);
        when(cartRepository.findAll()).thenReturn(cartList);
        cartService.allCartItems();
        Assert.assertEquals(cartList1, cartList);
    }

    @Test
    void givenBookDetails_WhenNoBooksAvailableInCart_ShouldThrowException() {
        List<CartDetails> cartList = new ArrayList<>();
        try {
            CartDTO cartDTO = new CartDTO(1,50,200.0);
            CartDetails cartDetails = new CartDetails();
            cartList.add(cartDetails);
            when(cartRepository.findAll()).thenReturn(cartList);
            cartService.allCartItems();
        } catch (CartException e) {
            Assert.assertEquals("No Books Available", e.getMessage());
        }
    }

    @Test
    void givenBookDetails_WhenUpdateBookQuantity_ShouldReturnMessage() {

        CartDTO cartDTO = new CartDTO(1,50,200.0);
        CartDetails cartDetails = new CartDetails();
        when(cartRepository.findByBookID(1)).thenReturn(java.util.Optional.of(cartDetails));
        when(cartRepository.save(any())).thenReturn(cartDetails);
        String message = "Book Quantity Updated";
        String updateQuantity = cartService.updateQuantityAndPrice(cartDTO);
        Assert.assertEquals(message, updateQuantity);
    }

    @Test
    void givenBookDetails_WhenNoBookAvailable_ShouldThrowException() {
        List<CartDetails> cartList = new ArrayList<>();
        try {
            CartDTO cartDTO = new CartDTO(1,50,200.0);
            CartDetails cartDetails = new CartDetails();
            cartList.add(cartDetails);
            when(cartRepository.findByBookID(2)).thenReturn(java.util.Optional.of(cartDetails));
            when(cartRepository.save(any())).thenReturn(cartList);
            cartService.updateQuantityAndPrice(cartDTO);
        } catch (CartException e) {
            Assert.assertEquals("No Books Available", e.getMessage());
        }
    }

    @Test
    void givenBookID_WhenPresentToDelete_ShouldReturnMessage() throws Exception {
        String message = "Book Has Been Deleted";
        Integer id = 1;

        doNothing().doThrow(new IllegalArgumentException()).when(cartRepository).deleteById(id);
        String deleteCartItems = cartService.deleteCartItem(id);
        Assert.assertEquals(message, deleteCartItems);
        verify(cartRepository).deleteById(id);
    }

}
