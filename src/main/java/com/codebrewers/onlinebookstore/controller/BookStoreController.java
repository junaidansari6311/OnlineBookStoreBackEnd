package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class BookStoreController {

    @Autowired
    IBookStoreService bookStoreService;

    @GetMapping("/books")
    public ResponseEntity<ResponseDto> allBooks(@RequestParam(defaultValue = "0") Integer PageNo,
                                                @RequestParam(defaultValue = "8") Integer PageSize,
                                                @RequestParam(defaultValue = "id") String sortBy) {
        List<BookDetails> list = bookStoreService.allBooks(PageNo, PageSize, sortBy);
        return new ResponseEntity(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/books/count")
    public ResponseEntity<ResponseDto> getTotalCount() {
        return new ResponseEntity(bookStoreService.getCount(), HttpStatus.OK);
    }
}
