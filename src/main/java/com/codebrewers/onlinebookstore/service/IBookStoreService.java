package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.model.BookDetails;

import java.util.List;

public interface IBookStoreService {

    String addBook(BookDTO bookDTO);
    List<BookDetails> allBooks();
}
