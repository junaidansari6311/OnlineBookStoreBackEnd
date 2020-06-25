package com.codebrewers.onlinebookstore.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Coupons {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer Id;

    public String couponsType;
    public Double discountPrice;
    public String description;
    public String expireCouponDate;


    public Coupons(String couponsType, Double discountPrice, String description, String expireCouponDate) {
        this.couponsType = couponsType;
        this.discountPrice = discountPrice;
        this.description = description;
        this.expireCouponDate = expireCouponDate;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
