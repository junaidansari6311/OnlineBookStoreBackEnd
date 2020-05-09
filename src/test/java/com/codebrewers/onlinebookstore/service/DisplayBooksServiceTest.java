package com.codebrewers.onlinebookstore.service;
import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class DisplayBooksServiceTest {

    @Mock
    IBookStoreRepository bookStoreRepository;

    @Test
    void getAllBooks() {
        List<BookDetails> bookDTOList = new ArrayList<>();
        BookDTO bookDTO = new BookDTO("IOT","Mark",
                "This is book about how internet of things can be applied.",
                "ABC123","jpg",200,50,2015);
        BookDetails bookDetails = new BookDetails(bookDTO);
        bookDTOList.add(bookDetails);
        when(bookStoreRepository.findAll()).thenReturn(bookDTOList);
        int size = bookDTOList.size();
        Assert.assertEquals(1, size);
    }
}
