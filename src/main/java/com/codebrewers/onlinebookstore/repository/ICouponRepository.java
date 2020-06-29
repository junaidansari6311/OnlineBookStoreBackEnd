package com.codebrewers.onlinebookstore.repository;

import com.codebrewers.onlinebookstore.model.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICouponRepository extends JpaRepository<Coupons, Integer> {

    @Query(value="select * from coupons where coupons_type <> :couponType",nativeQuery = true)
    List<Coupons> fetchCoupons (@Param("couponType") String couponType);

    Optional<Coupons> findByCouponsType(String coupons);
}