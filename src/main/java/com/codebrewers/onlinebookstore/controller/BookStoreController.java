package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import com.codebrewers.onlinebookstore.dto.SearchAndFilterResponseDTO;
import com.codebrewers.onlinebookstore.enums.BookStoreEnum;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

import org.springframework.http.MediaType;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
public class BookStoreController {

    private static final Logger logger = LoggerFactory.getLogger(BookStoreController.class);

    @Autowired
    IBookStoreService bookStoreService;

    @GetMapping("/books")
    public ResponseEntity<ResponseDTO> getAllBooks(@RequestParam(defaultValue = "1") Integer PageNo,
                                                   @RequestParam(defaultValue = "8") Integer PageSize,
                                                   @RequestParam(defaultValue = "id") String sortBy) {
        List<BookDetails> list = bookStoreService.allBooks((PageNo - 1), PageSize, sortBy);
        return new ResponseEntity(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/books/count")
    public ResponseEntity<ResponseDTO> getTotalCount() {
        return new ResponseEntity(bookStoreService.getCount(), HttpStatus.OK);
    }

    @GetMapping("/sort/{pageNo}/{searchText}/{selectedfield}")
    public ResponseEntity sort(@PathVariable String searchText, @PathVariable int pageNo, @PathVariable BookStoreEnum selectedfield) {
        SearchAndFilterResponseDTO allBooks = bookStoreService.findAllBooks(searchText, pageNo, selectedfield);
        return new ResponseEntity(allBooks, HttpStatus.OK);
    }

    @GetMapping("/books/image/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = bookStoreService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
