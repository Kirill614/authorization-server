package com.example.demo.oauth.token;

import java.util.List;

public class TokenResponse<A, R> {
    private A accessToken;
    private R refreshToken;

    public TokenResponse(A accessToken, R refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenResponse() { }

    public A getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(A accessToken) {
        this.accessToken = accessToken;
    }

    public R getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(R refreshToken) {
        this.refreshToken = refreshToken;
    }

}
