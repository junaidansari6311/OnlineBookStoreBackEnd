package com.codebrewers.onlinebookstore.utils;

import com.codebrewers.onlinebookstore.exception.JWTException;
import com.codebrewers.onlinebookstore.model.UserDetails;

public interface IToken {

     String generateLoginToken(UserDetails userDetails);

     int decodeJWT(String jwt) throws JWTException;

     String generateVerificationToken(UserDetails userDetails);

}
