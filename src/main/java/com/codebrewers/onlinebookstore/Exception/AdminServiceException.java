package com.codebrewers.onlinebookstore.Exception;

import lombok.Getter;

@Getter
public class AdminServiceException extends RuntimeException{
    private String message;

    public AdminServiceException(String message) {
        super(message);
        this.message = message;
    }

}
