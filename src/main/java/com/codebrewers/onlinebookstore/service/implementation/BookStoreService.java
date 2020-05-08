package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookStoreService implements IBookStoreService {

    @Autowired
    private IBookStoreRepository bookStoreRepository;

    @Override
    public List<BookDetails> allBooks() {
        List<BookDetails> bookList = bookStoreRepository.findAll();
        return bookList;
    }
}
