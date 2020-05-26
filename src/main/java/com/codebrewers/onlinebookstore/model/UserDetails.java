package com.codebrewers.onlinebookstore.model;

import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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



    public UserDetails() {
    }


    public UserDetails(RegistrationDTO registrationDTO) {
        this.emailID = registrationDTO.emailID;
        this.password = registrationDTO.password;
        this.mobileNumber = registrationDTO.mobileNumber;
        this.fullName = registrationDTO.fullName;
    }
}
