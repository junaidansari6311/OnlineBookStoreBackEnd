package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.model.Coupons;

import java.util.List;

public interface ICouponService {
    List<Coupons> fetchCoupon(String token);
}
