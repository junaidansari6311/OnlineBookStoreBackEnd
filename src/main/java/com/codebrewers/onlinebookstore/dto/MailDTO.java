package com.codebrewers.onlinebookstore.dto;

public class MailDTO {

    public String to_address;
    public String body;
    public String subject;

    public MailDTO(String to_address,String body,String subject) {
        this.to_address = to_address;
        this.body = body;
        this.subject = subject;
    }
}
