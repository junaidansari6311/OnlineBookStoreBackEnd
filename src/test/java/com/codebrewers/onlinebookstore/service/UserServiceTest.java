package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.IUserRepository;
import com.codebrewers.onlinebookstore.service.implementation.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    UserService userService;

    @MockBean
    FileProperties fileProperties;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void givenUserDetails_WhenRegistered_ShouldReturnMessage() {
        RegistrationDTO registrationDTO = new RegistrationDTO("Gajanan", "gajanan@gmail.com", "Gajanan@123", "8855885588");
        UserDetails userDetails = new UserDetails(registrationDTO);
        String message = "REGISTRATION SUCCESSFUL";
            when(userRepository.findByEmailID(any())).thenReturn(java.util.Optional.of(userDetails));
            when(userRepository.save(any())).thenReturn(message);
        String user = userService.userRegistration(registrationDTO);
        Assert.assertEquals(message, user);
    }
}
