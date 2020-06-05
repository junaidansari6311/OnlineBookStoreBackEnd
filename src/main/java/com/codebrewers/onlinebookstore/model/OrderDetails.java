package com.codebrewers.onlinebookstore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public Integer orderId;
    public Double totalPrice;

    public LocalDate orderPlacedDate;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "cartId")
    public CartDetails cart;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "userId")
    public UserDetails user;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "customer")
    public CustomerDetails customer;

    @JsonIgnore
    @OneToMany(mappedBy = "orderDetails")
    List<BookCartDetails> bookCartDetails;


    public OrderDetails() {
    }

    public OrderDetails(CartDetails cart, Integer orderId, UserDetails user, Double totalPrice, CustomerDetails customerDetails, List<BookCartDetails> bookCartDetails) {
        this.user = user;
        this.cart = cart;
        this.customer = customerDetails;
        this.totalPrice = totalPrice;
        this.orderPlacedDate = LocalDate.now();
        this.bookCartDetails = bookCartDetails;
        this.orderId = orderId;
    }

}
