package com.codebrewers.onlinebookstore.service;
import com.codebrewers.onlinebookstore.Exception.AdminServiceException;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.service.implementation.BookStoreService;
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
public class AdminBookStoreServiceTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    IBookStoreRepository bookStoreRepository;

    @Autowired
    ModelMapper modelMapper;

    @InjectMocks
    BookStoreService bookStoreService;


    @Test
    void givenBookDetails_WhenGetResponse_ShouldReturnBookDetails() {
        BookDTO bookDTO = new BookDTO("IOT","Mark","This is book about how internet of things can be applied.","ABC123","jpg",200,50,2015);
        BookDetails givenBook = new BookDetails(bookDTO);
        when(bookStoreRepository.save(any())).thenReturn(givenBook);
        String message="Book Added Successfully";
        String addedBooks = bookStoreService.getAddedBooks(bookDTO);

        Assert.assertEquals(message, addedBooks);
    }

    @Test
    void givenBookDetails_WhenISBNNumberAlreadyPresent_ShouldReturnException() {
        try {
            BookDTO bookDTO = new BookDTO("IOT", "Mark", "This is book about how internet of things can be applied.", "ABC123", "jpg", 200, 50, 2015);
            BookDetails addBook = new BookDetails(bookDTO);
            when(bookStoreRepository.save(any())).thenReturn(addBook);
            when(bookStoreRepository.findByIsbn(bookDTO.getIsbn())).thenReturn(java.util.Optional.of(addBook));
            bookStoreService.getAddedBooks(bookDTO);
        }catch(AdminServiceException e) {
            Assert.assertEquals("ISBN Number is Already Present",e.getMessage());
        }
    }

    @Test
    void givenBookDetails_WhenBookNameAndAuthorNameAlreadyPresent_ShouldReturnException() {
        try {
            BookDTO bookDTO = new BookDTO("IOT", "Mark", "This is book about how internet of things can be applied.", "ABC123", "jpg", 200, 50, 2015);
            BookDetails addBook = new BookDetails(bookDTO);
            when(bookStoreRepository.save(any())).thenReturn(addBook);
            when(bookStoreRepository.findByBookNameAndAuthorName(bookDTO.getBookName(),bookDTO.getAuthorName())).thenReturn(java.util.Optional.of(addBook));
            bookStoreService.getAddedBooks(bookDTO);
        }catch(AdminServiceException e) {
            Assert.assertEquals("Book Already Present with IOT and Mark",e.getMessage());
        }
    }

}
