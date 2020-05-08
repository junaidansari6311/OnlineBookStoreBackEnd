package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.model.BookDetails;

import java.util.List;

public interface IBookStoreService {

    List<BookDetails> allBooks();

}
