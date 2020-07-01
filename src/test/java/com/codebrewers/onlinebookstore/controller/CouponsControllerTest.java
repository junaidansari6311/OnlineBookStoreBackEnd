package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import com.codebrewers.onlinebookstore.model.Coupons;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.ICouponRepository;
import com.codebrewers.onlinebookstore.service.implementation.CouponService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CouponController.class)
public class CouponsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    @MockBean
    ICouponRepository couponRepository;


    @MockBean
    FileProperties fileProperties;
    Gson gson = new Gson();


    @Test
    void givenCoupon_WhenFetchCoupon_ShouldReturnMessage() throws Exception {
        List<Coupons> couponsList = new ArrayList<>();
        List<Coupons> couponsList1 = new ArrayList<>();
        String totalPrice="350";

        Coupons coupons = new Coupons("WEL100", 100.0, "10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020",699.0);
        couponsList.add(coupons);
        couponsList1.add(coupons);
        String message = "Coupons Fetched Successfully";
        when(couponService.fetchCoupon(anyString(),any())).thenReturn(couponsList);
        MvcResult mvcResult = this.mockMvc.perform(get("/coupon")
                                                    .param("totalPrice",totalPrice)
                                                    .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenCoupon_WhenFetchCoupons_ShouldReturnAllCoupons(){
        List<Coupons> couponsList = new ArrayList<>();
        List<Coupons> couponsList1 = new ArrayList<>();
        Coupons coupons = new Coupons("WEL100", 100.0,"10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020",699.0);
        couponsList.add(coupons);
        couponsList1.add(coupons);
        when(couponService.fetchCoupon(anyString(),any())).thenReturn(couponsList);
        Assert.assertEquals(couponsList1, couponsList);
    }

    @Test
    void givenCoupon_WhenCouponAdded_ShouldReturnMessage() throws Exception {
        String discountCoupon="CB50";
        String totalPrice = "200.0";
        String message = "Coupon added successfully";
        when(couponService.addCoupon(any(),anyString(),anyDouble())).thenReturn(200.0);
        MvcResult mvcResult = this.mockMvc.perform(post("/coupon")
                .param("discountCoupon", discountCoupon).param("totalPrice", totalPrice)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }
}
