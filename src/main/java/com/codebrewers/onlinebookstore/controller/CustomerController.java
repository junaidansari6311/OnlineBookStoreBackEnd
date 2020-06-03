package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CustomerController {

    @Autowired
    ICustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity CustomerDetails(@RequestHeader(value = "token") String token, @RequestBody CustomerDetailsDTO customerDetailsDTO){
        String customerDetails = customerService.getCustomerDetails(token, customerDetailsDTO);
        ResponseDTO responseDTO = new ResponseDTO(customerDetails,null);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/customer")
    public ResponseEntity getCustomerDetail(@RequestHeader(value = "token") String token){
        UserDetails customerDetails = customerService.getCustomerDetail(token);
        return new ResponseEntity(customerDetails,HttpStatus.OK);
    }

}
