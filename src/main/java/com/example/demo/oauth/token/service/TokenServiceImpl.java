package com.example.demo.oauth.token.service;

import com.example.demo.oauth.UserEntity;
import com.example.demo.oauth.token.AccessToken;
import com.example.demo.oauth.token.BaseOauth2Token;
import com.example.demo.oauth.token.RefreshToken;
import com.sun.xml.bind.v2.runtime.output.Encoded;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TokenServiceImpl implements TokenService {
    private static final String TOKEN_SECRET = "MaIpmIqr8se4foERaNk+eRjlpdK0VOQkz3jemlrE+P8=";
    public static final byte[] KEY_BYTES = TOKEN_SECRET.getBytes();

    @Override
    public AccessToken createAccessToken(String username,String clientId, Set<String> scopes) {
        Date issuedAt = new Date();
        Date expirationDate = new Date(new Date().getTime() + 1000000000);
        SecretKey key = new SecretKeySpec(KEY_BYTES, "HmacSHA256");
        String jws = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .claim("scope", "articles.read")
                .claim("client", clientId)
                .signWith(key)
                .compact();
        System.out.println("ACCESS TOKEN : " + jws);
        return new AccessToken(jws, issuedAt, expirationDate);
    }

    @Override
    public RefreshToken createRefreshToken(String username,String clientId, Set<String> scopes){
        Date issuedAt = new Date();
        Date expirationDate = new Date(new Date().getTime() + 10000000);
        SecretKey key = new SecretKeySpec(KEY_BYTES, "HmacSHA256");
        String jws = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .claim("scope", scopes)
                .claim("client", clientId)
                .signWith(key)
                .compact();

        return new RefreshToken(jws, issuedAt, expirationDate);
    }


}
