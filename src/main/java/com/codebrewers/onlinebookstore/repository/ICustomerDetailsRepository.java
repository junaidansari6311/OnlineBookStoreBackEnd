package com.codebrewers.onlinebookstore.repository;

import com.codebrewers.onlinebookstore.model.CustomerDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICustomerDetailsRepository extends JpaRepository<CustomerDetails, Integer> {

    List<CustomerDetails> findByUserDetailsOrderById(UserDetails user);

}
