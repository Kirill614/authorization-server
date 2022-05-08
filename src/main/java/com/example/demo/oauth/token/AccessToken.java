package com.example.demo.oauth.token;

import java.util.Date;

public class AccessToken extends BaseOauth2Token{
    public AccessToken(String value, Date issuedAt, Date expDate){
        super(value, issuedAt, expDate, true);
    }
}
