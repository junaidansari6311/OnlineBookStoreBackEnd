package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.exception.BookStoreException;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookStoreService implements IBookStoreService {

    @Autowired
    private IBookStoreRepository bookStoreRepository;


    @Override
    public List<BookDetails> allBooks(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<BookDetails> bookList = bookStoreRepository.findAll(paging);
        if (bookList.hasContent()) {
            return bookList.getContent();
        } else {
            throw new BookStoreException("No Books Available");
        }
    }

    @Override
    public int getCount() {
        List<BookDetails> totalBooks = bookStoreRepository.findAll();
        return totalBooks.size();
    }
}
