package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.SearchAndFilterResponseDTO;
import com.codebrewers.onlinebookstore.enums.BookStoreEnum;
import com.codebrewers.onlinebookstore.model.BookDetails;
import org.springframework.core.io.Resource;

import java.util.List;

public interface IBookStoreService {

    List<BookDetails> allBooks(Integer pageNo, Integer pageSize, String sort);

    int getCount();

    SearchAndFilterResponseDTO findAllBooks(String searchText, int pageNo, BookStoreEnum selectedfield);

    Resource loadFileAsResource(String fileName);
}
