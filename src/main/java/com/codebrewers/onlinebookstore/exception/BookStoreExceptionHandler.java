package com.codebrewers.onlinebookstore.exception;

import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BookStoreExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AdminServiceException.class)
    public ResponseEntity<ResponseDTO> adminServiceExceptionHandler(AdminServiceException e) {
        ResponseDTO responseDto = new ResponseDTO(e.getMessage(), null);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
