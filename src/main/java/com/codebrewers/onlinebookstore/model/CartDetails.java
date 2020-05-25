package com.codebrewers.onlinebookstore.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class CartDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @OneToMany(mappedBy = "cartDetails")
    public List<BookCartDetails> book;

    public CartDetails() {
    }

    public CartDetails(int id,List<BookCartDetails> bookCartDetails) {
        this.id= id;
        this.book=bookCartDetails;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<BookCartDetails> getBook() {
        return book;
    }

    public void setBook(List<BookCartDetails> items) {
        this.book = items;
    }
}