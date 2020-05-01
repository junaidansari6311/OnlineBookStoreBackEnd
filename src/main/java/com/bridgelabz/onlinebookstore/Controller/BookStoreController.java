package com.bridgelabz.onlinebookstore.Controller;

import com.bridgelabz.onlinebookstore.DTO.BookStoreDTO;
import com.bridgelabz.onlinebookstore.Service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookStoreController {

    @Autowired
    IBookStoreService bookStoreService;

    @PostMapping("/addbooks")
    public String addBooks(@RequestBody BookStoreDTO bookStoreDto) {
        return bookStoreService.getAddedBooks(bookStoreDto);
    }
}
