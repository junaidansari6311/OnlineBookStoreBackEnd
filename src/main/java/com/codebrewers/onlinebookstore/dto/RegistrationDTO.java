package com.codebrewers.onlinebookstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RegistrationDTO {

    @NotNull
    @Pattern(regexp = "^[A-Za-z. ]+[ ]*[A-Za-z.]*$", message = "Please Enter Full Name")
    public String fullName;
    @NotNull
    @Pattern(regexp = "^([a-zA-Z]{3,}([.|_|+|-]?[a-zA-Z0-9]+)?[@][a-zA-Z0-9]+[.][a-zA-Z]{2,3}([.]?[a-zA-Z]{2,3})?)$", message = "please Enter EmailID")
    public String emailID;
    @NotNull
    @Pattern(regexp = "^((?=[^@|#|&|%|$]*[@|&|#|%|$][^@|#|&|%|$]*$)*(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9#@$?]{8,})$", message = "Please Enter Password")
    public String password;
    @NotNull
    @Pattern(regexp = "^([6-9]{1}[0-9]{9})$", message = "Please Enter Mobile Number")
    public String mobileNumber;

    public RegistrationDTO(String fullName, String emailID, String password, String mobileNumber) {
        this.fullName = fullName;
        this.emailID = emailID;
        this.password = password;
        this.mobileNumber = mobileNumber;
    }
}
