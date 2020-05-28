package com.codebrewers.onlinebookstore.utils.implementation;


import com.codebrewers.onlinebookstore.exception.JWTException;
import com.codebrewers.onlinebookstore.model.UserDetails;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import com.codebrewers.onlinebookstore.utils.IToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Token implements IToken {


    @Autowired
    FileProperties jwtProperties;

    @Override
    public String generateLoginToken(UserDetails userDetails) {

        long currentTime = System.currentTimeMillis();

        return Jwts.builder()
                .setId(String.valueOf(userDetails.id))
                .setSubject(userDetails.fullName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime + jwtProperties.getJwtExpirationMs()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getJwtSecret())
                .compact();
    }

    @Override
    public int decodeJWT(String jwt) throws JWTException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getJwtSecret()).parseClaimsJws(jwt).getBody();

            return Integer.parseInt(claims.getId());
        } catch (ExpiredJwtException e) {
            throw new JWTException("session time out");
        }
    }

}
