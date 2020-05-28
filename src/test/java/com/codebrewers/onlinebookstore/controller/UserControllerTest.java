package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDto;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.service.implementation.UserService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    HttpServletResponse httpServletResponse;

    Gson gson = new Gson();

    @Test
    void givenUserRegistration_WhenAllValidationAreTrue_ShouldReturnMessage() throws Exception {
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan","gajanan@gmail.com","gajanan@123","8855885588");
        UserDetails userDetails = new UserDetails(registrationDTO);
        String stringConvertDTO = gson.toJson(userDetails);
        String message = "REGISTRATION SUCCESSFUL";
        when(userService.userRegistration(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDto responseDto = gson.fromJson(response, ResponseDto.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }


    @Test
    void givenUserLogin_WhenFieldsAreCorrect_ShouldReturnMessage() throws Exception {
        LoginDTO logInDTO = new LoginDTO("gajanan@gmail.com","Gajanan@123");
        UserDetails userDetails = new UserDetails(logInDTO);
        String stringConvertDTO = gson.toJson(userDetails);
        String message = "token";
        when(userService.userLogin(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        httpServletResponse.setHeader("Authorization",message);

        Assert.assertEquals("LOGIN SUCCESSFUL",response);
    }

    @Test
    void givenUserLogin_WhenEmailIDFieldIsEmpty_ShouldReturnErrorMessage() throws Exception {
        LoginDTO logInDTO = new LoginDTO("","gajanan@123");
        UserDetails userDetails = new UserDetails(logInDTO);
        String stringConvertDTO = gson.toJson(userDetails);
        String message = "please Enter EmailID";

        when(userService.userLogin(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDto responseDto = gson.fromJson(response, ResponseDto.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenUserLogin_WhenPasswordFieldIsEmpty_ShouldReturnErrorMessage() throws Exception {
        LoginDTO logInDTO = new LoginDTO("gajanan@gmail.com","");
        UserDetails userDetails = new UserDetails(logInDTO);
        String stringConvertDTO = gson.toJson(userDetails);
        String message = "please Enter Password";

        when(userService.userLogin(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDto responseDto = gson.fromJson(response, ResponseDto.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }
}
