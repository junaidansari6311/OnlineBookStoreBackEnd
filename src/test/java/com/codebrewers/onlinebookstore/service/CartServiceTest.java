package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.repository.IcartRepository;
import com.codebrewers.onlinebookstore.service.implementation.CartService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
public class CartServiceTest {

    @Mock
    IcartRepository cartRepository;

    @InjectMocks
    CartService cartService;

    @Test
    void givenBookDetails_WhenBookAddedInCart_ShouldReturnMessage() {
        CartDTO cartDTO = new CartDTO(1, 50, "IOT", "Mark", 500, "iot.jpg");
        CartDetails cartDetails = new CartDetails(cartDTO);
        when(cartRepository.save(any())).thenReturn(cartDetails);
        String message = "book addded";
        String addedBooks = cartService.addTOCart(cartDTO);
        Assert.assertEquals(message, addedBooks);
    }

    @Test
    void givenBookDetails_WhenBookAlreadyPresentInCart_ShouldThrowException() {
        try {
            CartDTO cartDTO = new CartDTO(1, 50, "IOT", "Mark", 500, "iot.jpg");
            CartDetails cartDetails = new CartDetails(cartDTO);
            when(cartRepository.findByBookName("IOT")).thenReturn(java.util.Optional.of(cartDetails));
            when(cartRepository.save(any())).thenReturn(cartDetails);
            cartService.addTOCart(cartDTO);
        }
        catch (CartException e){
            Assert.assertEquals("Book Already Present", e.getMessage());
        }
    }

    @Test
    void givenCart_ShouldReturnListOfBooks() {
        List<CartDetails> cartList = new ArrayList<>();
        List<CartDetails> cartList1 = new ArrayList<>();
        CartDTO cartDTO = new CartDTO(1, 50, "IOT", "Mark", 500, "iot.jpg");
        CartDetails cartDetails = new CartDetails(cartDTO);
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
            CartDTO cartDTO = new CartDTO(1, 50, "IOT", "Mark", 500, "iot.jpg");
            CartDetails cartDetails = new CartDetails(cartDTO);
            cartList.add(cartDetails);
            when(cartRepository.findAll()).thenReturn(cartList);
            cartService.allCartItems();
        }
        catch (CartException e){
            Assert.assertEquals("No Books Available", e.getMessage());
        }
    }

    @Test
    void givenBookDetails_WhenUpdateBookQuantity_ShouldReturnMessage() {

        CartDTO cartDTO = new CartDTO(1, 50, "IOT", "Mark", 500, "iot.jpg");
        CartDetails cartDetails = new CartDetails(cartDTO);
        when(cartRepository.findByBookID(1)).thenReturn(java.util.Optional.of(cartDetails));
        when(cartRepository.save(any())).thenReturn(cartDetails);
        String message = "Book Quantity Update";
        String updateQuantity = cartService.UpdateQuantity(cartDTO);
        Assert.assertEquals(message,updateQuantity);
    }

    @Test
    void givenBookDetails_WhenNoBookAvailable_ShouldThrowException() {
        List<CartDetails> cartList = new ArrayList<>();
        try {
            CartDTO cartDTO = new CartDTO(1, 50, "IOT", "Mark", 500, "iot.jpg");
            CartDetails cartDetails = new CartDetails(cartDTO);
            cartList.add(cartDetails);
            when(cartRepository.findByBookID(2)).thenReturn(java.util.Optional.of(cartDetails));
            when(cartRepository.save(any())).thenReturn(cartList);
            cartService.UpdateQuantity(cartDTO);
        }catch (CartException e) {
            Assert.assertEquals("No Books Available", e.getMessage());
        }
    }

    @Test
    void givenBookID_WhenPresentToDelete_ShouldReturnMessage() throws Exception {
        String message = "Cart Has Been Deleted";
        Integer id = 1;

        doNothing().doThrow(new IllegalArgumentException()).when(cartRepository).deleteById(id);
        String deleteCartItems = cartService.deleteCartItems(id);
        Assert.assertEquals(message,deleteCartItems);
        verify(cartRepository).deleteById(id);
    }

}
