package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.model.Coupons;
import com.codebrewers.onlinebookstore.repository.ICouponRepository;
import com.codebrewers.onlinebookstore.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService implements ICouponService {

    @Autowired
    ICouponRepository couponRepository;


    @Override
    public List<Coupons> fetchCoupon(String token) {
        return null;
    }
}