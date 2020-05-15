package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.dto.SearchAndFilterResponseDTO;
import com.codebrewers.onlinebookstore.enums.BookStoreEnum;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/books/{pageNo}/{searchtext}")
    public ResponseEntity<Page<BookDetails>> getBookByName(@PathVariable("pageNo") Integer PageNo,
                                                           @PathVariable("searchtext") String searchText) {
        Pageable pageable = PageRequest.of(PageNo,8);
        return new ResponseEntity(bookStoreService.searchBook(pageable,searchText), HttpStatus.OK);
    }

    @GetMapping("/sort/{pageNo}/{searchText}/{selectedfield}")
    public ResponseEntity sort(@PathVariable String searchText, @PathVariable int pageNo, @PathVariable BookStoreEnum selectedfield) {
        int size = bookStoreService.getSize(searchText);
        List<BookDetails> allBooks = bookStoreService.findAllBooks(searchText, pageNo, selectedfield);
        SearchAndFilterResponseDTO searchAndFilterResponseDTO = new SearchAndFilterResponseDTO(allBooks,size);
        return new ResponseEntity(searchAndFilterResponseDTO, HttpStatus.OK);
    }
}
