package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BookStoreController {

    @Autowired
    IBookStoreService bookStoreService;

    @GetMapping("/books")
    public ResponseEntity<ResponseDto> allBooks(){
        return new ResponseEntity(bookStoreService.allBooks(), HttpStatus.OK);
    }
}
