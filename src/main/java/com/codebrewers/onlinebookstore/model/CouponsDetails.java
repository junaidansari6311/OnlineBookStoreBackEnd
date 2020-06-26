package com.codebrewers.onlinebookstore.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class CouponsDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer Id;

    @ManyToOne
    @JoinColumn(name = "couponId")
    public Coupons coupons;

    @ManyToOne
    @JoinColumn(name = "userId")
    public UserDetails user;

    public CouponsDetails(Coupons coupons, UserDetails userDetails) {

        this.coupons = coupons;
        this.user = userDetails;

    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }
}
