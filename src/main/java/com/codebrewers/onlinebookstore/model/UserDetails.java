package com.codebrewers.onlinebookstore.model;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserDetails {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String emailID;
    @JsonIgnore
    public String password;

    public String mobileNumber;
    public String fullName;
    public boolean isVerified;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDetails")
    public List<CustomerDetails> customerDetails;

    @OneToMany(mappedBy = "user")
    public List<OrderDetails> orderDetails;

    public UserDetails() {
    }


    public UserDetails(LoginDTO logInDTO) {
        this.emailID = logInDTO.emailID;
        this.password = logInDTO.password;
    }

    public UserDetails(RegistrationDTO registrationDTO) {
        this.emailID = registrationDTO.emailID;
        this.password = registrationDTO.password;
        this.mobileNumber = registrationDTO.mobileNumber;
        this.fullName = registrationDTO.fullName;
        this.isVerified = registrationDTO.isVerified;
    }

    public List<CustomerDetails> getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(List<CustomerDetails> customerDetails) {
        this.customerDetails = customerDetails;
    }

}
