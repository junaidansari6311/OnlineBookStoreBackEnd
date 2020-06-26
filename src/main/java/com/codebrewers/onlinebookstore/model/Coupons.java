package com.codebrewers.onlinebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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


    @JsonIgnore
    @OneToMany(mappedBy = "coupons")
    public List<CouponsDetails> couponsDetails;

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
