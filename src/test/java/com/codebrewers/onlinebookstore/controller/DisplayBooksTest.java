package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.service.implementation.BookStoreService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookStoreController.class)
public class DisplayBooksTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookStoreService bookStoreService;

    @Test
    void findAllBooks() throws Exception {
        List<BookDetails> bookList = new ArrayList<>();
        BookDTO bookDTO = new BookDTO("IOT","Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123","jpg",50.00,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        bookList.add(bookDetails);
        when(bookStoreService.allBooks(0,8,"id")).thenReturn(bookList);
        this.mockMvc.perform(get("/books")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().json("[{'bookName':'IOT','authorName':'Peter'," +
                "'description':'This book about getting started with IOT by way of creating your own products.','imageUrl':'jpg'," +
                "'isbn':'iotBook123','bookPrice':50.0,'quantity':5.0,'publishingYear':2020}])"));
    }

    @Test
    void givenDisplayBook_WhenBooksAdded_ThenReturnListOfBooks() throws Exception {
        List<BookDetails> bookList = new ArrayList<>();
        BookDTO book1 = new BookDTO("IOT","Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123","jpg",50.00,5,2020);
        BookDTO book2 = new BookDTO("IOT","Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123","jpg",50.00,5,2020);
        BookDetails bookDetails1 = new BookDetails(book1);
        BookDetails bookDetails2 = new BookDetails(book2);

        bookList.add(bookDetails1);
        bookList.add(bookDetails2);

        when(bookStoreService.allBooks(0,4,"id")).thenReturn(bookList);
        int size = bookList.size();
        Assert.assertEquals(2,size);
    }

    @Test
    void givenDisplayBook_WhenWrongMethod_ThenReturn405StatusCode() throws Exception {
        List<BookDetails> bookDTOList = new ArrayList<>();
        BookDTO bookDTO = new BookDTO("IOT","Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123","jpg",50.00,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        bookDTOList.add(bookDetails);
        when(bookStoreService.allBooks(0,4,"id")).thenReturn(bookDTOList);
        int status = this.mockMvc.perform(post("/books"))
                .andReturn().getResponse().getStatus();
        Assert.assertEquals(405,status);
    }

}
