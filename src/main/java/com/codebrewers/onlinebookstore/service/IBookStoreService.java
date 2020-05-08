package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.BookDTO;

public interface IBookStoreService {

    String addBook(BookDTO bookDTO);
}
