package com.codebrewers.onlinebookstore.model;

import com.codebrewers.onlinebookstore.dto.BookDTO;

import javax.persistence.*;
import java.util.List;

@Entity
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer id;

    public String bookName;
    public String authorName;
    public String description;
    public String imageUrl;
    public String isbn;
    public double bookPrice;
    public double quantity;
    public int publishingYear;

    @OneToMany(mappedBy = "bookDetails")
    List<BookCartDetails> bookCartDetails;

    public BookDetails() {
    }

    public BookDetails(BookDTO bookDTO) {
        this.bookName = bookDTO.bookName;
        this.authorName = bookDTO.authorName;
        this.bookPrice = bookDTO.bookPrice;
        this.isbn = bookDTO.isbn;
        this.quantity = bookDTO.quantity;
        this.description = bookDTO.description;
        this.imageUrl = bookDTO.imageUrl;
        this.publishingYear = bookDTO.publishingYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
