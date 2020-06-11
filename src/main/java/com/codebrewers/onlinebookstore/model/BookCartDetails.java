package com.codebrewers.onlinebookstore.model;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class BookCartDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public Integer quantity;
    public Double totalPrice;

    public boolean orderStatus;
    public String addedToCartDate;

    @ManyToOne()
    @JoinColumn(name = "bookId")
    public BookDetails bookDetails;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "cartId")
    public CartDetails cartDetails;

    @ManyToOne()
    @JoinColumn(name = "orderId")
    public OrderDetails orderDetails;


    public BookCartDetails() {
    }

    public BookCartDetails(CartDTO cartDTO){
        this.quantity=cartDTO.quantity;
        this.orderStatus= false;
        this.totalPrice=cartDTO.totalPrice;
        this.addedToCartDate= LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }


    public void setCartDetails(CartDetails cartDetails) {
        this.cartDetails = cartDetails;
    }


    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
}
