package com.codebrewers.onlinebookstore.exception;

import lombok.Getter;

@Getter
public class BookStoreException extends RuntimeException {

    public BookStoreException(String message) {
        super(message);
    }
}
