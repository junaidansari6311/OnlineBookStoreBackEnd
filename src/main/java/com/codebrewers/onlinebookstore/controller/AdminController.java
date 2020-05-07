package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.service.IBookStoreService;
import com.codebrewers.onlinebookstore.dto.BookDTO;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class AdminController {

    @Autowired
    IBookStoreService bookStoreService;


    @PostMapping("/book")
    public ResponseEntity<ResponseDto> addBooks(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        String message = bookStoreService.getAddedBooks(bookDTO);
        ResponseDto responseDto=new ResponseDto(message,null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
