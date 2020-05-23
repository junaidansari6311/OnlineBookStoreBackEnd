package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.CartDTO;
import com.codebrewers.onlinebookstore.dto.MailDTO;
import com.codebrewers.onlinebookstore.exception.CartException;
import com.codebrewers.onlinebookstore.model.CartDetails;
import com.codebrewers.onlinebookstore.repository.ICartRepository;
import com.codebrewers.onlinebookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class CartService implements ICartService {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Autowired
    private ICartRepository icartRepository;


    @Override
    public String addToCart(CartDTO cartDTO) {
        CartDetails cartDetails = new CartDetails(cartDTO);
        Optional<CartDetails> byBookName = icartRepository.findByBookName(cartDTO.bookName);
        if (byBookName.isPresent()) {
            throw new CartException("Book Already Present");
        }
        icartRepository.save(cartDetails);
        return "book addded";
    }

    @Override
    public List<CartDetails> allCartItems() {
        List<CartDetails> all = icartRepository.findAll();
        if (all.isEmpty()) {
            throw new CartException("No Books Available");
        }
        return all;
    }

    @Override
    public String updateQuantity(CartDTO cartDTO) {
        Optional<CartDetails> byBookID = icartRepository.findByBookID(cartDTO.bookID);
        if (byBookID.isPresent()) {
            CartDetails cartDetails = byBookID.get();
            cartDetails.setQuantity(cartDTO.quantity);
            icartRepository.save(cartDetails);
            return "Book Quantity Update";
        }
        throw new CartException("No Books Available");
    }

    @Override
    public String deleteCartItem(Integer id) {
        icartRepository.deleteById(id);
        return "Cart Has Been Deleted";
    }

    @Override
    public void sendMail(MailDTO mailDTO) throws IOException, MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailDTO.to_address));
        msg.setSubject(mailDTO.subject);
        msg.setContent(mailDTO.body, "text/html");
        Transport.send(msg);
    }
}
