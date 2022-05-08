package com.example.demo.oauth.token.service;

import com.example.demo.oauth.UserEntity;
import com.example.demo.oauth.token.AccessToken;
import com.example.demo.oauth.token.BaseOauth2Token;
import com.example.demo.oauth.token.RefreshToken;

import java.util.List;

public interface TokenService {
    AccessToken createAccessToken(String username, String clientId, List<String> scopes);

    RefreshToken createRefreshToken(String username, String clientId, List<String> scopes);
}
