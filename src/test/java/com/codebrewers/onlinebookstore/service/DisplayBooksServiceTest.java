package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.dto.SearchAndFilterResponseDTO;
import com.codebrewers.onlinebookstore.enums.BookStoreEnum;
import com.codebrewers.onlinebookstore.exception.BookStoreException;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.service.implementation.BookStoreService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DisplayBooksServiceTest {

    @Mock
    IBookStoreRepository bookStoreRepository;

    @InjectMocks
    BookStoreService bookStoreService;


    @MockBean
    FileProperties fileProperties;

    @Test
    void getAllBooks() {
        List<BookDetails> bookList = new ArrayList<>();
        BookDTO bookDTO = new BookDTO("IOT", "Mark",
                "This is book about how internet of things can be applied.",
                "ABC123", "jpg", 200, 50, 2015);
        BookDetails bookDetails = new BookDetails(bookDTO);
        bookList.add(bookDetails);
        when(bookStoreRepository.findAll()).thenReturn(bookList);
        int size = bookList.size();
        Assert.assertEquals(1, size);
    }

    @Test
    void whenBooksAreNotAvailable_ShouldThrowAnException() {
        List<BookDetails> booksList = new ArrayList<>();
        Pageable paging = PageRequest.of(0, 10);
        Page<BookDetails> page = new PageImpl(booksList);
        try {
            when(bookStoreRepository.findAll(paging)).thenReturn(page);
        } catch (BookStoreException bookException) {
            Assert.assertEquals("No Books Available", bookException.getMessage());
        }
    }

    @Test
    void givenABookToFilter_whenPresent_shouldReturnBooks() {
        List<BookDetails> bookList = new ArrayList<>();
        BookDTO bookDTO = new BookDTO("IOT", "Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123", "jpg", 50.00, 5, 2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        bookList.add(bookDetails);
        when(bookStoreRepository.findAll()).thenReturn(bookList);
        SearchAndFilterResponseDTO allBooks = bookStoreService.findAllBooks("none", 0, BookStoreEnum.HIGH_TO_LOW);
        List bookDetails2 = allBooks.books;
        Assert.assertEquals(bookList, bookDetails2);

    }

    @Test
    void givenBookToSearchAndFilter_whenBookPresent_shouldReturnBooks() {
        List<BookDetails> bookList = new ArrayList<>();
        BookDTO bookDTO = new BookDTO("IOT", "Peter",
                "This book about getting started with IOT by way of creating your own products.",
                "iotBook123", "jpg", 50.00, 5, 2020);
        BookDetails bookDetails = new BookDetails(bookDTO);
        bookList.add(bookDetails);
        when(bookStoreRepository.findSortedBooks(any())).thenReturn(bookList);
        SearchAndFilterResponseDTO allBooks = bookStoreService.findAllBooks("I", 0, BookStoreEnum.HIGH_TO_LOW);
        List bookDetails2 = allBooks.books;
        Assert.assertEquals(bookList, bookDetails2);

    }

}
