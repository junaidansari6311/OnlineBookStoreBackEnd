package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.exception.AdminServiceException;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.service.implementation.AdminService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminServiceTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    IBookStoreRepository bookStoreRepository;

    @Autowired
    ModelMapper modelMapper;

    @InjectMocks
    AdminService adminService;


    @Test
    void givenBookDetails_WhenGetResponse_ShouldReturnBookDetails() {
        BookDTO bookDTO = new BookDTO("IOT", "Mark", "This is book about how internet of things can be applied.", "ABC123", "jpg", 200, 50, 2015);
        BookDetails givenBook = new BookDetails(bookDTO);
        when(bookStoreRepository.save(any())).thenReturn(givenBook);
        String message = "Book Added Successfully";
        String addedBooks = adminService.addBook(bookDTO);

        Assert.assertEquals(message, addedBooks);
    }

    @Test
    void givenBookDetails_WhenISBNNumberAlreadyPresent_ShouldReturnException() {
        try {
            BookDTO bookDTO = new BookDTO("IOT", "Mark", "This is book about how internet of things can be applied.", "ABC123", "jpg", 200, 50, 2015);
            BookDetails addBook = new BookDetails(bookDTO);
            when(bookStoreRepository.save(any())).thenReturn(addBook);
            when(bookStoreRepository.findByIsbn(bookDTO.isbn)).thenReturn(java.util.Optional.of(addBook));
            adminService.addBook(bookDTO);
        } catch (AdminServiceException e) {
            Assert.assertEquals("ISBN Number is Already Present", e.getMessage());
        }
    }

    @Test
    void givenBookDetails_WhenBookNameAndAuthorNameAlreadyPresent_ShouldReturnException() {
        try {
            BookDTO bookDTO = new BookDTO("IOT", "Mark", "This is book about how internet of things can be applied.", "ABC123", "jpg", 200, 50, 2015);
            BookDetails addBook = new BookDetails(bookDTO);
            when(bookStoreRepository.save(any())).thenReturn(addBook);
            when(bookStoreRepository.findByBookNameAndAuthorName(bookDTO.bookName, bookDTO.authorName)).thenReturn(java.util.Optional.of(addBook));
            adminService.addBook(bookDTO);
        } catch (AdminServiceException e) {
            Assert.assertEquals("Book Already Present with IOT and Mark", e.getMessage());
        }
    }

}
