package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.ICouponRepository;
import com.codebrewers.onlinebookstore.service.implementation.UserService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    HttpServletResponse httpServletResponse;

    @MockBean
    FileProperties fileProperties;

    @MockBean
    ICouponRepository couponRepository;

    HttpHeaders httpHeaders = new HttpHeaders();

    Gson gson = new Gson();

    @Test
    void givenUserRegistration_WhenAllValidationAreTrue_ShouldReturnMessage() throws Exception {
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan", "gajanan@gmail.com", "Gajanan@123", "8855885588", true);
        UserDetails userDetails = new UserDetails(registrationDTO);
        String stringConvertDTO = gson.toJson(userDetails);
        String message = "REGISTRATION SUCCESSFUL";
        when(userService.userRegistration(any(), any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenUserLogin_WhenFieldsAreCorrect_ShouldReturnMessage() throws Exception {
        LoginDTO logInDTO = new LoginDTO("gajanan@gmail.com", "Gajanan@123");
        UserDetails userDetails = new UserDetails(logInDTO);
        String stringConvertDTO = gson.toJson(userDetails);
        String message = "token";
        when(userService.userLogin(any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        httpServletResponse.setHeader("Authorization", message);

        Assert.assertEquals("LOGIN SUCCESSFUL", response);
    }

    @Test
    void givenUserLogin_WhenEmailIDFieldIsEmpty_ShouldReturnErrorMessage() throws Exception {
        LoginDTO logInDTO = new LoginDTO("", "Gajanan@123");
        UserDetails userDetails = new UserDetails(logInDTO);
        String stringConvertDTO = gson.toJson(userDetails);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andExpect(content().string("please Enter EmailID"))
                .andDo(print());
    }

    @Test
    void givenUserLogin_WhenPasswordFieldIsEmpty_ShouldReturnErrorMessage() throws Exception {
        LoginDTO logInDTO = new LoginDTO("gajanan@gmail.com", "");
        UserDetails userDetails = new UserDetails(logInDTO);
        String stringConvertDTO = gson.toJson(userDetails);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
                .content(stringConvertDTO)).andExpect(content().string("Please Enter Password"))
                .andDo(print());
    }

    @Test
    void givenPassword_WhenProper_ShouldReturnProperMessage() throws Exception {
        String password = "Gajanan@123";
        String urlToken = "gvfa454";
        String message = "Password Has Been Reset";
        when(userService.resetPassword(any(), any())).thenReturn(message);
        MvcResult mvcResult = this.mockMvc.perform(post("/user/confirm/password/")
                .param("password", password)
                .param("token", urlToken)
                .headers(httpHeaders).characterEncoding("utf-8")).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ResponseDTO responseDto = gson.fromJson(response, ResponseDTO.class);
        String responseMessage = responseDto.message;
        Assert.assertEquals(message, responseMessage);
    }

    @Test
    void givenUserRegistration_WhenMobileNumberFieldIsBlank_ShouldReturnProperMessage() throws Exception {
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan", "gajanan@gmail.com", "Gajanan@123", "", true);
        UserDetails userDetails = new UserDetails(registrationDTO);
        String stringConvertDTO = gson.toJson(userDetails);
        String message = "Please Enter Mobile Number";
        when(userService.userRegistration(any(), anyString())).thenReturn(message);
        MvcResult mvcResult = mockMvc.perform(post("/user/register")
                .content(stringConvertDTO)
                .contentType(MediaType.APPLICATION_JSON).headers(httpHeaders).characterEncoding("utf-8")).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(message, response);
    }

    @Test
    void givenUserRegistration_WhenUserVerifyEmail_ShouldReturnMessage() throws Exception {
        String urlToken = "gvfa454";
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan", "gajanan@gmail.com", "Gajanan@123", "", true);
        UserDetails userDetails = new UserDetails(registrationDTO);
        String stringConvertDTO = gson.toJson(userDetails);
        String message = "User Has Been Verified";
        when(userService.verifyEmail(anyString())).thenReturn(message);
        MvcResult result = mockMvc.perform(post("/user/verify/mail")
                .param("token", urlToken)
                .content(stringConvertDTO)
                .contentType(MediaType.APPLICATION_JSON).headers(httpHeaders).characterEncoding("utf-8")).andReturn();
        String response = result.getResponse().getContentAsString();
        Assert.assertEquals(message, response);
    }
}
