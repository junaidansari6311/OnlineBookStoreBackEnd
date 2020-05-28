package com.codebrewers.onlinebookstore.utils;

import com.codebrewers.onlinebookstore.model.UserDetails;

public interface IToken {

     String generateLoginToken(UserDetails userDetails);

}
