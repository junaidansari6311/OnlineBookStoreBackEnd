package com.bridgelabz.onlinebookstore.DTO;

public class BookStoreDTO {

    public String bookName;
    public String authorName;
    public String bookDetail;
    public String imageUrl;
    public double bookPrice;
    public double quantity;
    public int publishingYear;

    public BookStoreDTO(String bookName, String authorName, String bookDetail, String imageUrl, double bookPrice, double quantity, int publishingYear) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookDetail = bookDetail;
        this.imageUrl = imageUrl;
        this.bookPrice = bookPrice;
        this.quantity = quantity;
        this.publishingYear = publishingYear;
    }


}
