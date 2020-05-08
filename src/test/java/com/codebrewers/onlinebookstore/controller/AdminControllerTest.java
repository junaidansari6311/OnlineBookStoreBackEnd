package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.exception.AdminServiceException;
import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.service.implementation.AdminService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    Gson gson = new Gson();

    @Test
    void givenBookDetails_WhenAdded_ShouldReturnsBooks() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String stringConvertDTO = gson.toJson(bookDetails);
        String message="Book Added Successfully";
        when(adminService.getAddedBooks(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andReturn();

        String response= mvcResult.getResponse().getContentAsString();
        ResponseDto responseDto = gson.fromJson(response,ResponseDto.class);
        String responseMessage=responseDto.message;
        Assert.assertEquals(message,responseMessage);
    }

    @Test
    void givenBookDetails_WhenAdded_ShouldReturnStatus() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String message="Book Added Successfully";
        String stringConvertDTO = gson.toJson(bookDetails);
        when(adminService.getAddedBooks(any())).thenReturn(message);
        this.mockMvc.perform(post("/book")
                .content(stringConvertDTO).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void givenBookDetails_WhenWrongData_ShouldReturn400StatusCode() throws Exception {
        AdminServiceException adminServiceException=new AdminServiceException("");
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        when(adminService.getAddedBooks(any())).thenThrow(adminServiceException);
        int status = this.mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();
        Assert.assertEquals(400,status);
    }

    @Test
    void givenBookDetails_WhenWrongURL_ShouldReturn404StatusCode() throws Exception {
        AdminServiceException adminServiceException=new AdminServiceException("");
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String stringConvertDTO = gson.toJson(bookDetails);
        when(adminService.getAddedBooks(any())).thenThrow(adminServiceException);
        int status = this.mockMvc.perform(post("/insertbook")
                .content(stringConvertDTO).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();
        Assert.assertEquals(404,status);
    }

    @Test
    void givenBookDetails_WhenWrongMediaType_ShouldReturn415StatusCode() throws Exception {
        AdminServiceException adminServiceException=new AdminServiceException("");
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String stringConvertDTO = gson.toJson(bookDetails);
        when(adminService.getAddedBooks(any())).thenThrow(adminServiceException);
        int status = this.mockMvc.perform(post("/book")
                .content(stringConvertDTO).contentType(MediaType.APPLICATION_ATOM_XML)).andReturn().getResponse().getStatus();
        Assert.assertEquals(415,status);
    }

    @Test
    void givenBookDetails_WhenWrongMethod_ShouldReturn405StatusCode() throws Exception {
        AdminServiceException adminServiceException=new AdminServiceException("");
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        String stringConvertDTO = gson.toJson(bookDetails);
        when(adminService.getAddedBooks(any())).thenThrow(adminServiceException);
        int status = this.mockMvc.perform(get("/book")
                .content(stringConvertDTO).contentType(MediaType.APPLICATION_ATOM_XML))
                .andReturn().getResponse().getStatus();
        Assert.assertEquals(405,status);
    }

 @Test
 void givenBookDetails_WhenMissingAnyField_ShouldReturnError() throws Exception {
     BookDTO bookDTO = new BookDTO("","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
     BindingResult bindingResult=mock(BindingResult.class);
     String stringConvertDTO = gson.toJson(bookDTO);
     when(bindingResult.hasErrors()).thenReturn(true);
     mockMvc.perform(post("/book")
             .content(stringConvertDTO)
             .contentType(MediaType.APPLICATION_JSON_VALUE))
             .andExpect(status().isNotAcceptable())
             .andDo(print());
 }

    @Test
    void givenBookDetails_WhenBookNameMissing_ShouldReturnError() throws Exception {
        BookDTO bookDTO = new BookDTO("","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BindingResult bindingResult=mock(BindingResult.class);
        String stringConvertDTO = gson.toJson(bookDTO);
        when(bindingResult.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/book")
                .content(stringConvertDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("Please Provide Book Name"))
                .andDo(print());
    }

    @Test
    void givenBookDetails_WhenAuthorNameMissing_ShouldReturnError() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","","This book about getting started with IOT by way of creating your own products.","iotBook123","jpg",50.0,5,2020);
        BindingResult bindingResult=mock(BindingResult.class);
        String stringConvertDTO = gson.toJson(bookDTO);
        when(bindingResult.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/book")
                .content(stringConvertDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("Please Provide Author Name"))
                .andDo(print());
    }

    @Test
    void givenBookDetails_WhenDescriptionMissing_ShouldReturnError() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","","iotBook123","jpg",50.0,5,2020);
        BindingResult bindingResult=mock(BindingResult.class);
        String stringConvertDTO = gson.toJson(bookDTO);
        when(bindingResult.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/book")
                .content(stringConvertDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("Please Provide Description"))
                .andDo(print());
    }

    @Test
    void givenBookDetails_WhenIsbnMissing_ShouldReturnError() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","","jpg",50.0,5,2020);
        BindingResult bindingResult=mock(BindingResult.class);
        String stringConvertDTO = gson.toJson(bookDTO);
        when(bindingResult.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/book")
                .content(stringConvertDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("Please Provide ISBN"))
                .andDo(print());
    }

    @Test
    void givenBookDetails_WhenImageMissing_ShouldReturnError() throws Exception {
        BookDTO bookDTO = new BookDTO("IOT","Peter","This book about getting started with IOT by way of creating your own products.","iotBook123","",50.0,5,2020);
        BindingResult bindingResult=mock(BindingResult.class);
        String stringConvertDTO = gson.toJson(bookDTO);
        when(bindingResult.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/book")
                .content(stringConvertDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string("Please Provide Image Name"))
                .andDo(print());
    }

}
