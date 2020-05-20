package com.codebrewers.onlinebookstore.repository;

import com.codebrewers.onlinebookstore.model.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IcartRepository extends JpaRepository<CartDetails, Integer> {
    Optional<CartDetails> findByBookName(String bookName);

    Optional<CartDetails> findByBookID(int bookID);
}