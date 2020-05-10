package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminService adminService;


    @PostMapping("/book")
    public ResponseEntity<ResponseDto> addBooks(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        String message = adminService.addBook(bookDTO);
        ResponseDto responseDto=new ResponseDto(message,null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
