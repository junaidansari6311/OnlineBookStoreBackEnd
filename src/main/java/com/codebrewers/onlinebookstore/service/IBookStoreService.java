package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.model.BookDetails;

public interface IBookStoreService {

    BookDetails getAddedBooks(BookDTO bookDTO);
}
