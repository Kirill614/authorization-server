package com.example.demo.oauth.token;

import java.util.Date;

public class BaseOauth2Token {
    private String tokenValue;
    private Date issuedAt;
    private Date expirationDate;
    private boolean isAccessToken;

    public BaseOauth2Token(String tokenValue, Date issuedAt, Date expirationDate, boolean isAccessToken) {
        this.tokenValue = tokenValue;
        this.issuedAt = issuedAt;
        this.expirationDate = expirationDate;
        this.isAccessToken = isAccessToken;
    }

    public BaseOauth2Token(){ }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isAccessToken() {
        return isAccessToken;
    }

    public void setAccessToken(boolean accessToken) {
        isAccessToken = accessToken;
    }
}
