package com.bridgelabz.onlinebookstore.Controller;

import com.bridgelabz.onlinebookstore.DTO.BookStoreDTO;
import com.bridgelabz.onlinebookstore.Service.Implementation.BookStoreService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(BookStoreController.class)
public class BookStoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookStoreService bookStoreService;

    Gson gson = new Gson();

    @Test
    void givenBookDetails_WhenAdded_ShouldReturnsBooks() throws Exception {
        BookStoreDTO bookStoreDTO = new BookStoreDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook",150,2,2019);
        String stringConvertDTO = gson.toJson(bookStoreDTO);
        when(bookStoreService.getAddedBooks(any())).thenReturn(stringConvertDTO);
        MvcResult mvcResult = this.mockMvc.perform(post("/insertbook")
                .contentType(MediaType.APPLICATION_JSON).content(stringConvertDTO)).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(stringConvertDTO,response);
    }
}
