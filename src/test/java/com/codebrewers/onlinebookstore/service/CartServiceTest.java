package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.repository.IcartRepository;
import com.codebrewers.onlinebookstore.service.implementation.CartService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
}
