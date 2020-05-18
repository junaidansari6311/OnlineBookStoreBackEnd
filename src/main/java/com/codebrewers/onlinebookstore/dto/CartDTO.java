package com.codebrewers.onlinebookstore.dto;

public class CartDTO {
    public int bookID;
    public int quantity;
    public String bookName;
    public String authorName;
    public double bookPrice;
    public String bookImg;

    public CartDTO() {
    }

    public CartDTO(int bookID, int quantity, String bookName, String authorName, double bookPrice, String bookImg) {
        this.bookID = bookID;
        this.quantity = quantity;
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookPrice = bookPrice;
        this.bookImg = bookImg;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
