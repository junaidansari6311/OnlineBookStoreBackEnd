package com.codebrewers.onlinebookstore.model;

import com.codebrewers.onlinebookstore.dto.CartDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CartDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public Integer bookID;

    public double quantity;

    public String bookName;
    public String authorName;
    public double bookPrice;
    public String bookImg;

    public CartDetails() {
    }

    public CartDetails(CartDTO cartDTO) {
        this.bookID = cartDTO.bookID;
        this.quantity = cartDTO.quantity;
        this.bookName = cartDTO.bookName;
        this.authorName = cartDTO.authorName;
        this.bookPrice = cartDTO.bookPrice;
        this.bookImg = cartDTO.bookImg;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
