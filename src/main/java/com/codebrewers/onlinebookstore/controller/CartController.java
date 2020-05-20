package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.service.ICartService;
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

    @PostMapping("/cart")
    public ResponseEntity<ResponseDto> addBooks(@Valid @RequestBody CartDTO cartDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        String message = cartService.addTOCart(cartDTO);
        ResponseDto responseDto = new ResponseDto(message, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<ResponseDto> allCart(){
        List<CartDetails> list = cartService.allCartItems();
        return new ResponseEntity(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/cart")
    public ResponseEntity Update(@Valid @RequestBody CartDTO cartDTO){
        String message = cartService.UpdateQuantity(cartDTO);
        ResponseDto responseDto = new ResponseDto(message, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity deleteCart(@PathVariable Integer id){
        String message = cartService.deleteCartItems(id);
        ResponseDto responseDto = new ResponseDto(message,null);
        return new ResponseEntity(responseDto,HttpStatus.OK);
    }
}
