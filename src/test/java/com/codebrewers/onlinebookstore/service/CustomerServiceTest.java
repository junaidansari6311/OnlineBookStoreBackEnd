package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.model.CustomerDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.implementation.CustomerService;
import com.codebrewers.onlinebookstore.utils.implementation.Token;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    CustomerService customerService;

    @MockBean
    FileProperties fileProperties;

    @Mock
    Token jwtToken;

    List<CustomerDetails> customerDetails = new ArrayList<>();

    @Test
    void givenUserDetail_WhenProper_ShouldProperMessage() {
        String token="asbfj45";
        UserDetails user;
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan","gajanan@gmail.com","XYTZ@1456","9966998855",true);
        user= new UserDetails(registrationDTO);
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO("400604","Thane",
                "16/17 A Street","Thane","Thane", "HOME");
        CustomerDetails customer = new CustomerDetails(customerDetailsDTO);
        customer.setUserDetails(user);
        user.setCustomerDetails(customerDetails);
        String message="Customer Details Added Successful";
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(jwtToken.decodeJWT(anyString())).thenReturn(1);
        String customerDetails1 = customerService.getCustomerDetails(token, customerDetailsDTO);
        Assert.assertEquals(message,customerDetails1);
    }

    @Test
    void givenUserDetail_WhenProper_ShouldProperMessages() {
        String token="asbfj45";
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan","gajanan@gmail.com","XYTZ@1456","9966998855",true);
        UserDetails user=new UserDetails(registrationDTO);
        UserDetails user1=new UserDetails(registrationDTO);
        when(jwtToken.decodeJWT(anyString())).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));
        customerService.getCustomerDetail(token);
        Assert.assertEquals(user.fullName,user1.fullName);

    }

}
