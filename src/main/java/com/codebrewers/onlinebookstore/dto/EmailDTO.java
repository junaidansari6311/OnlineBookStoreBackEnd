package com.codebrewers.onlinebookstore.dto;

public class EmailDTO {

    public String email;
    public String subject;
    public String message;

    public EmailDTO() {
    }

    public EmailDTO(String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }
}
