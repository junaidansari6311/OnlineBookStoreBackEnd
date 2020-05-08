package com.codebrewers.onlinebookstore.model;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String bookName;
    public String authorName;
    public String description;
    public String imageUrl;
    public String isbn;
    public double bookPrice;
    public double quantity;
    public int publishingYear;

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
}
