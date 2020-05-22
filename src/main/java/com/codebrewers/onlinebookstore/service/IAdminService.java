package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IAdminService {

    String addBook(BookDTO bookDTO);

    String storeFile(MultipartFile file);
}
