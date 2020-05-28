package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> userRegistration(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        String message = userService.userRegistration(registrationDTO);
        ResponseDto responseDTO = new ResponseDto(message, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity userLogin(@Valid @RequestBody LoginDTO logInDTO, BindingResult bindingResult, HttpServletResponse response){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        String userLogin = userService.userLogin(logInDTO);
        response.setHeader("Authorization",userLogin);
        return new ResponseEntity("LOGIN SUCCESSFUL", HttpStatus.OK);
    }
}
