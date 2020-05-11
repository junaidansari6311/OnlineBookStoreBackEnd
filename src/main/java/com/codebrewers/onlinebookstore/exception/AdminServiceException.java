package com.codebrewers.onlinebookstore.exception;

import lombok.Getter;

@Getter
public class AdminServiceException extends RuntimeException {

    public AdminServiceException(String message) {
        super(message);
    }
}
