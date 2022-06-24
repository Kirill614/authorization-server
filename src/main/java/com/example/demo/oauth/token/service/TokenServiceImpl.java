package com.example.demo.oauth.token.service;

import com.example.demo.converter.UserDetailsConverter;
import com.example.demo.oauth.token.AccessToken;
import com.example.demo.oauth.token.RefreshToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class TokenServiceImpl implements TokenService {
    private static final String TOKEN_SECRET = "MaIpmIqr8se4foERaNk+eRjlpdK0VOQkz3jemlrE+P8=";
    public static final byte[] KEY_BYTES = TOKEN_SECRET.getBytes();

    @Override
    public AccessToken createAccessToken(String username, String clientId, Set<String> scopes) {
        Date issuedAt = new Date();
        Date expirationDate = new Date(new Date().getTime() + 1000000000);
        SecretKey key = new SecretKeySpec(KEY_BYTES, "HmacSHA256");
        String jws = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .claim("scope", scopes)
                .claim("client", clientId)
                .signWith(key)
                .compact();

        return new AccessToken(jws, issuedAt, expirationDate);
    }

    @Override
    public RefreshToken createRefreshToken(String username, String clientId, Set<String> scopes) {
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
