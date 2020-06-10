package com.codebrewers.onlinebookstore.dto;

public class ResponseDTO {
    public String message;
    public Object bookDetails;

    public ResponseDTO(String message, Object bookDetails) {
        this.message = message;
        this.bookDetails = bookDetails;
    }

    public ResponseDTO(String message) {
        this.message = message;
    }
}
