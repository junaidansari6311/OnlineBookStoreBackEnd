package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.model.BookDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookStoreService {

    List<BookDetails> allBooks(Integer pageNo, Integer pageSize, String sort);

    int getCount();

    Page<BookDetails> searchBook(Pageable pageable, String field);
}
