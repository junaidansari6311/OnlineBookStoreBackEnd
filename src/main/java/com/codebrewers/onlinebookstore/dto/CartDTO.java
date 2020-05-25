package com.codebrewers.onlinebookstore.dto;

public class CartDTO {

    public Integer id;


    public Integer quantity;
    public Double totalPrice;

    public CartDTO() {
    }

    public CartDTO(Integer id, Integer quantity,Double totalPrice) {
        this.id = id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}