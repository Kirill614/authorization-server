package com.example.demo.oauth.token;

import java.util.Date;

public class RefreshToken extends BaseOauth2Token{
    public RefreshToken(String value, Date issuedAt, Date expirationDate){
        super(value, issuedAt, expirationDate, false);
    }
}
