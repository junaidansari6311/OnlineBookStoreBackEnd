package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import com.codebrewers.onlinebookstore.model.Coupons;
import com.codebrewers.onlinebookstore.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class CouponController {

    @Autowired
    ICouponService couponService;

    @GetMapping("/coupon")
    public ResponseEntity fetchOrderCoupon(@RequestHeader(value = "token", required = false) String token) {
        List<Coupons> orders = couponService.fetchCoupon(token);
        ResponseDTO response = new ResponseDTO("Coupons Fetched Successfully", orders);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
