package com.codebrewers.onlinebookstore.dto;

import com.codebrewers.onlinebookstore.model.BookDetails;

public class ResponseDTO {
    public String message;
    public BookDetails bookDetails;

    public ResponseDTO(String message, BookDetails bookDetails) {
        this.message = message;
        this.bookDetails = bookDetails;
    }

    public ResponseDTO(String message) {
        this.message = message;
    }
}
