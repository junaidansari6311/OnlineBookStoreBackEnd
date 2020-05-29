package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.service.implementation.CartService;
import com.codebrewers.onlinebookstore.utils.IToken;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;


    @MockBean
    FileProperties fileProperties;

    Gson gson = new Gson();

    @Test
    void givenBookDetails_WhenAddedToCart_ShouldReturnMessage() throws Exception {
        List<CartDetails> cart1 = new ArrayList<>();
        CartDetails cartDetails = new CartDetails();
        cart1.add(cartDetails);
        String stringConvertDTO = gson.toJson(cartDetails);
        String message = "Book Added To Cart Successfully";
        when(cartService.addToCart(any(),any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenBookDetails_WhenWrongData_ShouldReturn400StatusCode() throws Exception {
        CartDTO cartDTO = new CartDTO(1,50,200.0);
        String message = "book Added";
        when(cartService.addToCart(any(),any())).thenReturn(message);
        int status = this.mockMvc.perform(post("/cart")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getStatus();

        Assert.assertEquals(400, status);
    }

    @Test
    void givenBookDetails_WhenWrongMethod_ShouldReturn405StatusCode() throws Exception {
        CartDTO cartDTO = new CartDTO(1,50,200.0);
        String message = "Book Added Successfully";
        when(cartService.addToCart(any(),any())).thenReturn(message);
        int status = this.mockMvc.perform(post("/cart/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getStatus();

        Assert.assertEquals(405, status);
    }

    @Test
    void givenCart_shouldReturnsListOfAllBooks() throws Exception {
        List<CartDetails> cartList = new ArrayList<>();
        List<CartDetails> cartList1 = new ArrayList<>();
        CartDTO cartDTO = new CartDTO(1,50,200.0);
        CartDetails cartDetails = new CartDetails();
        cartList.add(cartDetails);
        cartList1.add(cartDetails);

        when(cartService.allCartItems()).thenReturn(cartList);
        Assert.assertEquals(cartList1, cartList);
    }


    @Test
    void givenBookDetails_WhenUpdateBookQuantity_ShouldReturnMessage() throws Exception {
        List<CartDetails> cart1 = new ArrayList<>();
        CartDTO cartDTO = new CartDTO(1,50,200.0);
        CartDetails cartDetails = new CartDetails();
        cart1.add(cartDetails);
        String stringConvertDTO = gson.toJson(cartDetails);
        String message = "Book Quantity Update";
        when(cartService.updateQuantityAndPrice(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(put("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenBookID_WhenPresentToDelete_ShouldReturnMessage() throws Exception {
        String message = "Cart Has Been Deleted";
        Integer id = 1;
        when(cartService.deleteCartItem(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(delete("/cart/{id}", id)).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }
}
