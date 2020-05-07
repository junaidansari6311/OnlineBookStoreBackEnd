package com.codebrewers.onlinebookstore.dto;

import com.codebrewers.onlinebookstore.model.BookDetails;
import lombok.Getter;

@Getter
public class ResponseDto {
    public String message;
    public BookDetails bookDetails;

    public ResponseDto(String message, BookDetails bookDetails) {
        this.message = message;
        this.bookDetails = bookDetails;
    }
}
