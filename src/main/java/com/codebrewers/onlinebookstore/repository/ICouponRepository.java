package com.codebrewers.onlinebookstore.repository;

import com.codebrewers.onlinebookstore.model.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICouponRepository extends JpaRepository<Coupons, Integer> {

}