package com.example.demo.oauth.token;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryTokenRepository {
    private Map<String, TokenResponse> tokens = new HashMap<>();

    public void saveTokenResponse(String username, TokenResponse tokenResponse) {
        Assert.notNull(tokenResponse, "token response is not created");
        Assert.notNull(tokenResponse.getAccessToken(), "access token is not created");
        Assert.notNull(tokenResponse.getRefreshToken(), "refresh token is not created");
        tokens.put(username, tokenResponse);
    }

    public TokenResponse getTokenResponse(String username) throws Exception {
        if (tokens.get(username) == null) throw new Exception("token was not found");
        return tokens.get(username);
    }
}
