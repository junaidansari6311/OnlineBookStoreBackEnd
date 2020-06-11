package com.codebrewers.onlinebookstore.dto;

public class ResponseDTO {
    public String message;
    public Object data;

    public ResponseDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(String message) {
        this.message = message;
    }
}
