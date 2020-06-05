package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.exception.UserServiceException;
import com.codebrewers.onlinebookstore.model.*;
import com.codebrewers.onlinebookstore.repository.*;
import com.codebrewers.onlinebookstore.service.IOrderService;
import com.codebrewers.onlinebookstore.utils.implementation.MailService;
import com.codebrewers.onlinebookstore.utils.implementation.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    @Autowired
    Token jwtToken;
    @Autowired
    MailService mailService;
    @Autowired
    IOrderRepository orderRepository;
    @Autowired
    IBookCartDetailsRepository cartDetailsRepository;
    @Autowired
    IBookStoreRepository bookStoreRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    ICustomerDetailsRepository customerDetailsRepository;
    @Autowired
    ICartRepository cartRepository;


    @Override
    public Integer placeOrder(Double totalPrice, String token) throws MessagingException {
        return null;
    }


}
