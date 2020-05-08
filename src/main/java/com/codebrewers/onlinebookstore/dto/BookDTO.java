package com.codebrewers.onlinebookstore.dto;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
public class BookDTO {
    @NotEmpty(message = "Please Provide Book Name")
    public String bookName;
    @NotEmpty(message = "Please Provide Author Name")
    public String authorName;
    @NotEmpty(message = "Please Provide Description")
    public String description;
    @NotEmpty(message = "Please Provide Image Name")
    public String imageUrl;
    @NotEmpty(message = "Please Provide ISBN")
    public String isbn;
    @NotNull(message = "Please Provide Book Price")
    public double bookPrice;
    @NotNull(message = "Please Provide Quantity")
    public int quantity;
    @NotNull(message = "Please Provide Publishing Year")
    public int publishingYear;

    public BookDTO(String bookName, String authorName, String description, String isbn, String imageUrl, double bookPrice, int quantity, int publishingYear) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookPrice = bookPrice;
        this.isbn = isbn;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publishingYear = publishingYear;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public int getPublishingYear() {
        return publishingYear;
    }
}
