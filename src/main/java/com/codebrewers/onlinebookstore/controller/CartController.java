package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import com.codebrewers.onlinebookstore.model.BookCartDetails;
import com.codebrewers.onlinebookstore.service.ICartService;
import com.codebrewers.onlinebookstore.utils.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class CartController {

    @Autowired
    ICartService cartService;

    @Autowired
    IToken token;

    @PostMapping("/cart")
    public ResponseEntity<ResponseDTO> addBooks(@Valid @RequestBody CartDTO cartDTO,@RequestHeader(value = "token",required = false) String token,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        String message = cartService.addToCart(cartDTO,token);
        ResponseDTO responseDto = new ResponseDTO(message, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<ResponseDTO> fetchBooks(@RequestHeader(value = "token",required = false) String token) {
        List<BookCartDetails> list =  cartService.allCartItems(token);
        ResponseDTO responseDTO = new ResponseDTO("Response Successful", list);
        return new ResponseEntity(responseDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/cart")
    public ResponseEntity updateBookQuantity(@Valid @RequestBody CartDTO cartDTO,@RequestHeader(value = "token",required = false) String token) {
        String message = cartService.updateQuantityAndPrice(cartDTO,token);
        ResponseDTO responseDto = new ResponseDTO(message, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity deleteBook(@PathVariable Integer id) {
        String message = cartService.deleteCartItem(id);
        ResponseDTO responseDto = new ResponseDTO(message, null);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
}
