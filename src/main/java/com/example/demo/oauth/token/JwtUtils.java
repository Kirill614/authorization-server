package com.example.demo.oauth.token;

import com.example.demo.exceptions.NotValidTokenException;
import com.example.demo.oauth.token.service.TokenServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JwtUtils {
    private static SecretKey key = new SecretKeySpec(TokenServiceImpl.KEY_BYTES,
            "HmacSHA256");

    public static boolean validateJwt(String token) {
        Claims claims = getClaimsFromToken(token);
        if(claims.isEmpty()){
            throw new NotValidTokenException();
        }
        return true;
    }

    public static String parseClientIdFromToken(String token){
        Claims claims = getClaimsFromToken(token);
        if(claims.get("client") != null){
            return claims.get("client").toString();
        }
        return null;
    }

    public static String parseUsernameFromToken(String token){
        Claims claims = getClaimsFromToken(token);
        if(!claims.isEmpty()){
            if(claims.getSubject() != null){
                return claims.getSubject().toString();
            }
        }
        return null;
    }

    private static Claims getClaimsFromToken(String token){
         Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

         return claims;
    }
}
