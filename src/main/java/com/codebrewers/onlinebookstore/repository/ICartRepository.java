package com.codebrewers.onlinebookstore.repository;

import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartRepository extends JpaRepository<CartDetails, Integer> {
    Optional<CartDetails> findByUserDetails(UserDetails userDetails);
}