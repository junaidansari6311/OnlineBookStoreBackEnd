package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.service.implementation.CartService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    Gson gson = new Gson();

    @Test
    void givenBookDetails_WhenAddedToCart_ShouldReturnMessage() throws Exception {
        List<CartDetails> cart1 = new ArrayList<>();
        CartDTO cartDTO = new CartDTO(1, 50, "IOT", "Mark", 500, "iot.jpg");
        CartDetails cartDetails = new CartDetails(cartDTO);
        cart1.add(cartDetails);
        String stringConvertDTO = gson.toJson(cartDetails);
        String message = "book Added";
        when(cartService.addTOCart(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDto responseDto = gson.fromJson(response, ResponseDto.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }
}
