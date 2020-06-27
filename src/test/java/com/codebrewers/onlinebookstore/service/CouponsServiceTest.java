package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.Coupons;
import com.codebrewers.onlinebookstore.model.CouponsDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.ICouponRepository;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.implementation.CouponService;
import com.codebrewers.onlinebookstore.service.implementation.ICouponDetailsRepository;
import com.codebrewers.onlinebookstore.utils.implementation.Token;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CouponsServiceTest {

    @Mock
    ICouponRepository couponRepository;

    @Mock
    ICouponDetailsRepository couponDetailsRepository;

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    CouponService couponService;

    @MockBean
    FileProperties fileProperties;

    @Mock
    Token jwtToken;

    @Test
    void givenCoupon_WhenFetchCoupon_ShouldReturnCoupons() {
        List<Coupons> couponsList = new ArrayList<>();
        Coupons coupons = new Coupons("WEL100", 100.0,"10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020");
        couponsList.add(coupons);
        couponsList.add(coupons);
        couponsList.add(coupons);
        LoginDTO logInDTO = new LoginDTO("gajanan@gmail.com", "Gajanan@123");
        UserDetails userDetails = new UserDetails(logInDTO);
        when(jwtToken.decodeJWT(anyString())).thenReturn(1);
        when(couponRepository.findAll()).thenReturn(couponsList);
        List<Coupons> coupons1 = couponService.fetchCoupon("token");
        Assert.assertEquals(couponsList,coupons1);
    }

    @Test
    void givenCoupon_WhenNoCouponAvailable_ShouldThrowException() {
        List<CouponsDetails> couponsDetailsList = new ArrayList<>();
        List<Coupons> couponsList = new ArrayList<>();
        Coupons coupons = new Coupons("WEL100", 100.0,"10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020");
        LoginDTO logInDTO = new LoginDTO("junaid@gmail.com", "Junaid@123");
        UserDetails userDetails = new UserDetails(logInDTO);
        CouponsDetails couponsDetails = new CouponsDetails(coupons,userDetails);
        couponsDetailsList.add(couponsDetails);
        String message = "Coupons Not Available";
        try {
            when(jwtToken.decodeJWT(anyString())).thenReturn(1);
            when(couponRepository.findAll()).thenReturn(couponsList);
            when(couponDetailsRepository.findByUserId(anyInt())).thenReturn(couponsDetailsList);
            couponService.fetchCoupon("token");
        }catch (UserServiceException e) {
            Assert.assertEquals(message,e.getMessage());
        }
    }

    @Test
    void givenCoupon_WhenAppliedOneCoupon_ShouldReturnRemainingCoupon() {
        List<CouponsDetails> couponsDetailsList = new ArrayList<>();
        List<Coupons> couponsList = new ArrayList<>();
        Coupons coupons = new Coupons("WEL100", 100.0,"10% Off upto Rs.100 on minimum purchase of Rs.699.0", "30-06-2020");
        couponsList.add(coupons);
        couponsList.add(coupons);
        couponsList.add(coupons);
        LoginDTO logInDTO = new LoginDTO("junaid@gmail.com", "Junaid@123");
        UserDetails userDetails = new UserDetails(logInDTO);
        CouponsDetails couponsDetails = new CouponsDetails(coupons,userDetails);
        couponsDetailsList.add(couponsDetails);
        when(jwtToken.decodeJWT(anyString())).thenReturn(1);
        when(couponRepository.findAll()).thenReturn(couponsList);
        when(couponDetailsRepository.findByUserId(anyInt())).thenReturn(couponsDetailsList);
        List<Coupons> coupons1 = couponService.fetchCoupon("token");
        Assert.assertEquals(couponsList,coupons1);
    }
}
