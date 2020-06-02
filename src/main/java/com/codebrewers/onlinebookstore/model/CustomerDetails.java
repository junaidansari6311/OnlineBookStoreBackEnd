package com.codebrewers.onlinebookstore.model;

import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class CustomerDetails {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String pincode;
    public String locality;
    public String address;
    public String city;
    public String landmark;

    @Enumerated(EnumType.STRING)
    public AddressType addressType;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "userId")
    public UserDetails userDetails;

    public CustomerDetails() {
    }

    public CustomerDetails(CustomerDetailsDTO orderBookDTO) {
        this.pincode = orderBookDTO.pincode;
        this.locality = orderBookDTO.locality;
        this.address = orderBookDTO.address;
        this.city = orderBookDTO.city;
        this.landmark = orderBookDTO.landmark;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

}
