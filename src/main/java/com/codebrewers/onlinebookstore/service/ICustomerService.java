package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.model.UserDetails;

public interface ICustomerService {

    String getCustomerDetails(String token, CustomerDetailsDTO customerDetailsDTO);

    UserDetails getCustomerDetail(String token);

}
