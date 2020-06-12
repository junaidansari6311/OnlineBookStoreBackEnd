package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.model.*;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.repository.*;
import com.codebrewers.onlinebookstore.service.implementation.OrderService;
import com.codebrewers.onlinebookstore.utils.IMailService;
import com.codebrewers.onlinebookstore.utils.implementation.MailService;
import com.codebrewers.onlinebookstore.utils.implementation.Token;
import com.codebrewers.onlinebookstore.utils.templet.PlacedOrder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    IOrderRepository orderRepository;

    @Mock
    IUserRepository userRepository;

    @Mock
    ICartRepository cartRepository;

    @Mock
    ICustomerDetailsRepository customerDetailsRepository;

    @Mock
    IBookCartDetailsRepository bookCartDetailsRepository;

    @Mock
    IBookStoreRepository bookStoreRepository;

    @InjectMocks
    OrderService orderService;

    @MockBean
    FileProperties fileProperties;

    @Mock
    MailService mailService;

    @Mock
    PlacedOrder placedOrder;

    @Mock
    Token jwtToken;

    List<CustomerDetails> customerDetails = new ArrayList<>();

    @Test
    void givenOrderDetails_WhenOrderPlaced_ShouldReturnOrderID() throws IOException, MessagingException {
        List<BookCartDetails> bookCartDetails = new ArrayList<>();
        LoginDTO loginDTO = new LoginDTO("gajanan@gmail.com","Gajanan@123");
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO("400604","Thane",
                "16/17 A Street","Thane","Thane", "HOME");
        CartDetails cartDetails = new CartDetails();
        UserDetails userDetails = new UserDetails(loginDTO);
        CustomerDetails customer = new CustomerDetails(customerDetailsDTO);
        OrderDetails order = new OrderDetails(cartDetails, 123456, userDetails, 1000.0, customer, bookCartDetails);
        customerDetails.add(customer);
        cartDetails.setId(1);
        cartDetails.setBook(bookCartDetails);
        cartDetails.setUserDetails(userDetails);
        order.orderId=123456;
        when(jwtToken.decodeJWT(any())).thenReturn(1);
        when(bookCartDetailsRepository.fetchCartItems(anyInt())).thenReturn(bookCartDetails);
        when(customerDetailsRepository.findByUserDetailsOrderById(any())).thenReturn(customerDetails);
        when(orderRepository.save(any())).thenReturn(order);
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(userDetails));
        when(cartRepository.findByUserDetails(any())).thenReturn(java.util.Optional.of(cartDetails));
        when(bookStoreRepository.updateBookQuantity(any(),any())).thenReturn(1);
        when(bookCartDetailsRepository.updateOrderPlacedStatus(anyInt())).thenReturn(1);
        when(placedOrder.getHeader(any(),any(),any(),any())).thenReturn("Placed");
        when(mailService.sendMail(any(),any(),any())).thenReturn("Mail has been send");
        Integer token = orderService.placeOrder(1000.0, "token");
        Assert.assertEquals(123456,token,0.0);
    }

    @Test
    void givenOrderDetails_WhenOrderABooks_ShouldReturnSizeOfBooks(){
        List<BookCartDetails> bookCartDetails = new ArrayList<>();
        List<CartDetails> cartList = new ArrayList<>();
        LoginDTO loginDTO = new LoginDTO("gajanan@gmail.com","Gajanan@123");
        CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO("400604","Thane",
                "16/17 A Street","Thane","Thane", "HOME");
        CartDTO cartDTO = new CartDTO(1, 50,200.0);
        CustomerDetails customer = new CustomerDetails(customerDetailsDTO);
        UserDetails userDetails = new UserDetails(loginDTO);
        CartDetails cartDetails = new CartDetails();
        cartList.add(cartDetails);
        customerDetails.add(customer);
        OrderDetails order = new OrderDetails(cartDetails, 123456, userDetails, 300.0, customer, bookCartDetails);
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(order);
        order.id = 1;
        BookCartDetails bookCartDetails1 = new BookCartDetails(cartDTO);
        bookCartDetails.add(bookCartDetails1);

        when(jwtToken.decodeJWT(any())).thenReturn(1);
        when(cartRepository.findByUserDetails(any())).thenReturn(java.util.Optional.of(cartDetails));
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(userDetails));
        when(orderRepository.findOrderDetailsByUser(any())).thenReturn(orderDetailsList);
        when(orderRepository.findById(anyInt())).thenReturn(java.util.Optional.of(order));
        when(customerDetailsRepository.findByUserDetailsOrderById(any())).thenReturn(customerDetails);
        List<BookCartDetails> bookCartOrder = orderService.fetchOrders("token");
        Assert.assertEquals(bookCartDetails.size(),bookCartOrder.size());
    }

}
