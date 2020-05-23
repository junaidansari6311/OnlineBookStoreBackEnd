package com.codebrewers.onlinebookstore.dto;

public class OrderBookDetailsDTO {

    public String[] bookName;

    public Integer quantity;

    public Double bookPrice;

    public String customerName;

    public String mobileNo;

    public String pincode;

    public String locality;

    public String address;

    public String city;

    public String landmark;

    public String email;


    public OrderBookDetailsDTO(Integer quantity, Double bookPrice, String customerName, String mobileNo, String pincode, String locality, String address, String city, String landmark, String email, String... bookName) {
        this.quantity = quantity;
        this.bookPrice = bookPrice;
        this.customerName = customerName;
        this.mobileNo = mobileNo;
        this.pincode = pincode;
        this.locality = locality;
        this.address = address;
        this.city = city;
        this.landmark = landmark;
        this.email = email;
        this.bookName = bookName;

    }
}
