package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.Coupons;
import com.codebrewers.onlinebookstore.model.CouponsDetails;
import com.codebrewers.onlinebookstore.repository.ICouponRepository;
import com.codebrewers.onlinebookstore.service.ICouponService;
import com.codebrewers.onlinebookstore.utils.implementation.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService implements ICouponService {

    @Autowired
    ICouponRepository couponRepository;

    @Autowired
    Token jwtToken;

    @Autowired
    ICouponDetailsRepository couponDetailsRepository;


    @Override
    public List<Coupons> fetchCoupon(String token) {
        int userId = jwtToken.decodeJWT(token);
        List<Coupons> coupons = couponRepository.findAll();

        List<CouponsDetails> couponsDetails = couponDetailsRepository.findByUserId(userId);

        for (CouponsDetails couponDetails1 : couponsDetails) {
            coupons.remove(couponDetails1.coupons);
        }
        if (coupons.isEmpty())
            throw new UserServiceException("Coupons Not Available");

        return coupons;
    }

    @Override
    public Double addCoupon(String token, String coupon, Double totalPrice) {
        return null;
    }
}