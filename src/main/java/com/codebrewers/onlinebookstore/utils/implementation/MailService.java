package com.codebrewers.onlinebookstore.utils.implementation;

import com.codebrewers.onlinebookstore.dto.CustomerDetailsDTO;
import com.codebrewers.onlinebookstore.utils.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Value;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailService implements IMailService {

    @Autowired
    JavaMailSender javaMailSender;


    public void sendMail(CustomerDetailsDTO order) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(order.email);
        helper.setSubject("Order Placed");
        helper.setText("Dear, "+order.customerName+" Congratulations! Your order for the books is Successfully Placed."
                +"\n Your Book Name Are : "+
                String.join(" , ",order.bookName) +"\n Total Book Price : "+order.bookPrice+"\n Total No. Of Books : "+order.quantity);
        javaMailSender.send(message);
    }
}
