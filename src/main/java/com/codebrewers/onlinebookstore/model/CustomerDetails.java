package com.codebrewers.onlinebookstore.model;

import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "customer")
    List<OrderDetails> orderDetailsList;

    public CustomerDetails() {
    }

    public CustomerDetails(CustomerDetailsDTO orderBookDTO) {
        this.pincode = orderBookDTO.pincode;
        this.locality = orderBookDTO.locality;
        this.address = orderBookDTO.address;
        this.city = orderBookDTO.city;
        this.landmark = orderBookDTO.landmark;
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
