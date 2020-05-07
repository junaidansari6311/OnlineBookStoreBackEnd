package com.codebrewers.onlinebookstore.Exception;

import lombok.Getter;

@Getter
public class AdminServiceException extends RuntimeException{

    public AdminServiceException(String message) {
        super(message);
    }

}
