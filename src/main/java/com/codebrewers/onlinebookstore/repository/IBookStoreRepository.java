package com.codebrewers.onlinebookstore.repository;

import com.codebrewers.onlinebookstore.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookStoreRepository extends JpaRepository<BookDetails,Integer> {
    Optional<BookDetails> findByIsbn(String Isbn);
    Optional<BookDetails> findByBookNameAndAuthorName(String bookName,String authorName);
}
