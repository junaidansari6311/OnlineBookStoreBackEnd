package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.enums.AddressType;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.CustomerDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.ICustomerService;
import com.codebrewers.onlinebookstore.utils.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    IToken jwtToken;

    @Override
    public String getCustomerDetails(String token, CustomerDetailsDTO customerDetailsDTO) {
        int userId = jwtToken.decodeJWT(token);
        UserDetails user = userRepository.findById(userId).orElseThrow(() -> new UserServiceException("User Not Found"));

        CustomerDetails customerDetails = new CustomerDetails(customerDetailsDTO);

        List<CustomerDetails> customerDetailsList = new ArrayList<>();
        customerDetailsList.add(customerDetails);
        user.getCustomerDetails().add(customerDetails);
        user.setCustomerDetails(customerDetailsList);
        customerDetails.setUserDetails(user);
        customerDetails.setAddressType(AddressType.valueOf(customerDetailsDTO.addressType));

        userRepository.save(user);
        return "Customer Details Added Successful";
    }

    @Override
    public UserDetails getCustomerDetail(String token) {
        int userId = jwtToken.decodeJWT(token);
        Optional<UserDetails> user = userRepository.findById(userId);
        return user.get();
    }

}
