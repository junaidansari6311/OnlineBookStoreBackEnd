package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.service.implementation.BookStoreService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookStoreService bookStoreService;

    Gson gson = new Gson();

    @Test
    void givenBookDetails_WhenAdded_ShouldReturnsBooks() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String stringConvertDTO = gson.toJson(bookDetails);
        ResponseDto responseDto=new ResponseDto("Book Added Successfully",bookDetails);
        String responseDtoJson = gson.toJson(responseDto);
        when(bookStoreService.getAddedBooks(any())).thenReturn(bookDetails);
        this.mockMvc.perform(post("/book")
                .content(stringConvertDTO)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(content().json(responseDtoJson));
    }

    @Test
    void givenBookDetails_WhenAdded_ShouldReturnStatus() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String stringConvertDTO = gson.toJson(bookDetails);
        when(bookStoreService.getAddedBooks(any())).thenReturn(bookDetails);
        this.mockMvc.perform(post("/book")
                .content(stringConvertDTO).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void givenBookDetails_WhenWrongData_ShouldReturn400StatusCode() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        when(bookStoreService.getAddedBooks(any())).thenReturn(bookDetails);
        int status = this.mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();
        Assert.assertEquals(400,status);
    }

    @Test
    void givenBookDetails_WhenWrongURL_ShouldReturn404StatusCode() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String stringConvertDTO = gson.toJson(bookDetails);
        when(bookStoreService.getAddedBooks(any())).thenReturn(bookDetails);
        int status = this.mockMvc.perform(post("/insertbook")
                .content(stringConvertDTO).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();
        Assert.assertEquals(404,status);
    }

    @Test
    void givenBookDetails_WhenWrongMediaType_ShouldReturn415StatusCode() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String stringConvertDTO = gson.toJson(bookDetails);
        when(bookStoreService.getAddedBooks(any())).thenReturn(bookDetails);
        int status = this.mockMvc.perform(post("/book")
                .content(stringConvertDTO).contentType(MediaType.APPLICATION_ATOM_XML)).andReturn().getResponse().getStatus();
        Assert.assertEquals(415,status);
    }

    @Test
    void givenBookDetails_WhenWrongMethod_ShouldReturn405StatusCode() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String stringConvertDTO = gson.toJson(bookDetails);
        when(bookStoreService.getAddedBooks(any())).thenReturn(bookDetails);
        int status = this.mockMvc.perform(get("/book")
                .content(stringConvertDTO).contentType(MediaType.APPLICATION_ATOM_XML)).andReturn().getResponse().getStatus();
        Assert.assertEquals(405,status);
    }

}
