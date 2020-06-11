package com.codebrewers.onlinebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
public class CartDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @OneToMany(mappedBy = "cartDetails")
    @Where(clause = "order_status=true")
    public List<BookCartDetails> book;

    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "userId")
    public UserDetails userDetails;

    public CartDetails() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<BookCartDetails> getBook() {
        return book;
    }

    public void setBook(List<BookCartDetails> items) {
        this.book = items;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}