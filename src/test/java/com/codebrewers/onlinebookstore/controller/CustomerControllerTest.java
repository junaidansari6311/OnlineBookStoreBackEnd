package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.CustomerDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.ICouponRepository;
import com.codebrewers.onlinebookstore.service.implementation.CustomerService;
import com.codebrewers.onlinebookstore.utils.implementation.Token;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @MockBean
    Token jwtToken;

    @MockBean
    FileProperties fileProperties;

    @MockBean
    ICouponRepository couponRepository;

    Gson gson = new Gson();

    HttpHeaders httpHeaders=new HttpHeaders();

    @Test
    void givenUserDetail_WhenProper_ShouldReturnProperMessage() throws Exception {
        httpHeaders.set("token","Qwebst43Y");
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO("400604","Thane",
                "16/17 A Street","Thane","Thane", "HOME");
        CustomerDetails customerDetails = new CustomerDetails(customerDetailsDTO);
        customerDetails.id=1;
        String customerDetailsString = gson.toJson(customerDetails);
        String message = "Customer Details Added Successful";
        when(customerService.getCustomerDetails(anyString(),any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/customer").content(customerDetailsString)
                .contentType(MediaType.APPLICATION_JSON)
        .headers(httpHeaders).characterEncoding("utf-8")).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void getCustomerDetails() throws Exception {
        httpHeaders.set("token","Qwebst43Y");
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan","gajanan@gmailcom","Gajanan@123","8855885588",true);
        UserDetails userDetails = new UserDetails(registrationDTO);
        String customerDetailsString = gson.toJson(userDetails);
        when(customerService.getCustomerDetail(any())).thenReturn(userDetails);
        this.mockMvc.perform(get("/customer").content(customerDetailsString)
                .contentType(MediaType.APPLICATION_JSON).headers(httpHeaders).characterEncoding("utf-8")).andExpect(status().isOk());
    }

    @Test
    void givenUserDetail_WhenWrongURL_ShouldReturn404StatusCode() throws Exception {
        UserServiceException userServiceException =new UserServiceException("");
        httpHeaders.set("token","Qwebst43Y");
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO("400604","Thane",
                "16/17 A Street","Thane","Thane", "home");
        CustomerDetails customerDetails = new CustomerDetails(customerDetailsDTO);
        customerDetails.id=1;
        String customerDetailsString = gson.toJson(customerDetails);
        when(customerService.getCustomerDetails(anyString(),any())).thenThrow(userServiceException);
        int status = this.mockMvc.perform(post("/user/customer").content(customerDetailsString)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders).characterEncoding("utf-8")).andReturn().getResponse().getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    void givenUserDetail_WhenPincodeNotProper_ShouldReturnProperMessage() throws Exception {
        httpHeaders.set("token","Qwebst43Y");
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO("400604","Thane",
                "16/17 A Street","Thane","Thane", "home");
        CustomerDetails customerDetails = new CustomerDetails(customerDetailsDTO);
        customerDetails.id=1;
        String customerDetailsString = gson.toJson(customerDetails);
        String message = "Please enter a valid 6 digits zip code";
        when(customerService.getCustomerDetails(anyString(),any())).thenReturn(message);
        MvcResult mvcResult = mockMvc.perform(post("/customer")
                .content(customerDetailsString)
                .contentType(MediaType.APPLICATION_JSON_VALUE).headers(httpHeaders).characterEncoding("utf-8")).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenUserDetail_WhenLocalityNotProper_ShouldReturnProperMessage() throws Exception {
        httpHeaders.set("token","Qwebst43Y");
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO("400604","Thane52",
                "16/17 A Street","Thane","Thane", "home");
        CustomerDetails customerDetails = new CustomerDetails(customerDetailsDTO);
        customerDetails.id=1;
        String customerDetailsString = gson.toJson(customerDetails);
        String message = "Please enter valid location";
        when(customerService.getCustomerDetails(anyString(),any())).thenReturn(message);
        MvcResult mvcResult = mockMvc.perform(post("/customer")
                .content(customerDetailsString)
                .contentType(MediaType.APPLICATION_JSON_VALUE).headers(httpHeaders).characterEncoding("utf-8")).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenUserDetail_WhenAddressIsMoreThan150Character_ShouldReturnProperMessage() throws Exception {
        httpHeaders.set("token","Qwebst43Y");
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO("400604","Thane52",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. ",
                "Thane","Thane", "home");
        CustomerDetails customerDetails = new CustomerDetails(customerDetailsDTO);
        customerDetails.id=1;
        String customerDetailsString = gson.toJson(customerDetails);
        String message = "Please enter Address between 150 character";
        when(customerService.getCustomerDetails(anyString(),any())).thenReturn(message);
        MvcResult mvcResult = mockMvc.perform(post("/customer")
                .content(customerDetailsString)
                .contentType(MediaType.APPLICATION_JSON_VALUE).headers(httpHeaders).characterEncoding("utf-8")).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenUserDetail_WhenCityNameNotProper_ShouldReturnProperMessage() throws Exception {
        httpHeaders.set("token","Qwebst43Y");
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO("400604","Thane",
                "16/17 A Street","Thane12","Thane", "home");
        CustomerDetails customerDetails = new CustomerDetails(customerDetailsDTO);
        customerDetails.id=1;
        String customerDetailsString = gson.toJson(customerDetails);
        String message = "Please enter valid city name";
        when(customerService.getCustomerDetails(anyString(),any())).thenReturn(message);
        MvcResult mvcResult = mockMvc.perform(post("/customer")
                .content(customerDetailsString)
                .contentType(MediaType.APPLICATION_JSON_VALUE).headers(httpHeaders).characterEncoding("utf-8")).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

}
