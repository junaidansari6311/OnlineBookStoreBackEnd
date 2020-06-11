package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.dto.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IAdminService {

    String addBook(BookDTO bookDTO);

    UploadFileResponse storeFile(MultipartFile file);
}
