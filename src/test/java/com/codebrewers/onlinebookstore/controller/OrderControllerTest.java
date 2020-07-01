package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import com.codebrewers.onlinebookstore.model.BookCartDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.ICouponRepository;
import com.codebrewers.onlinebookstore.service.implementation.OrderService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    FileProperties fileProperties;

    @MockBean
    ICouponRepository couponRepository;

    Gson gson = new Gson();
    HttpHeaders httpHeaders = new HttpHeaders();

    @Test
    void givenOrderDetails_WhenOrderPlaced_ShouldReturnMessage() throws Exception {
        String message = "Order Placed Successfully";
        String totalprice="200";
        String discountPrice="100";

        when(orderService.placeOrder(anyDouble(), any(),any())).thenReturn(552255);
        MvcResult mvcResult = this.mockMvc.perform(post("/order")
                                                    .param("totalprice",totalprice)
                                                    .param("discountPrice",discountPrice))
                                           .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenOrderDetails_WhenCartDataFetch_ShouldReturnMessage() throws Exception {
        List<BookCartDetails> cartList = new ArrayList<>();
        String message = "Order Data Fetched Successfully";
        when(orderService.fetchOrders(anyString())).thenReturn(cartList);
        MvcResult mvcResult = this.mockMvc.perform(get("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders).characterEncoding("utf-8")).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }
}
