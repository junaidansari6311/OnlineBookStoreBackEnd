package com.codebrewers.onlinebookstore.exception;

public class JWTException extends RuntimeException {
    public JWTException(String message) {
        super(message);
    }
}
