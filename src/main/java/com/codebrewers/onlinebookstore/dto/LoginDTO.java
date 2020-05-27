package com.codebrewers.onlinebookstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginDTO {
    @NotNull
    @Pattern(regexp = "^([a-zA-Z]{3,}([.|_|+|-]?[a-zA-Z0-9]+)?[@][a-zA-Z0-9]+[.][a-zA-Z]{2,3}([.]?[a-zA-Z]{2,3})?)$", message = "please Enter EmailID")
    public String emailID;
    @NotNull
    @Pattern(regexp = "^((?=[^@|#|&|%|$]*[@|&|#|%|$][^@|#|&|%|$]*$)*(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9#@$?]{8,})$", message = "Please Enter Password")
    public String password;

    public LoginDTO(String emailID, String password) {
        this.emailID = emailID;
        this.password = password;
    }
}
