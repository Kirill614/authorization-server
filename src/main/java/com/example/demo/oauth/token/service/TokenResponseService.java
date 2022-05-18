package com.example.demo.oauth.token.service;

import com.example.demo.oauth.authCode.AuthorizationCode;
import com.example.demo.oauth.token.AccessToken;
import com.example.demo.oauth.token.RefreshToken;
import com.example.demo.oauth.token.TokenResponse;

import java.util.Set;

public interface TokenResponseService {
    TokenResponse consumeAuthCodeAndGenerateTokens(String code, Set<String> scopes) throws Exception;

    TokenResponse<AccessToken, RefreshToken> refreshToken(String refreshToken);

    TokenResponse<AccessToken, RefreshToken> generateTokens(String clientId, Set<String> scopes);
}
