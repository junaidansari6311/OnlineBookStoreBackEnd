package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.BookDTO;

public interface IAdminService {

    String addBook(BookDTO bookDTO);
}
