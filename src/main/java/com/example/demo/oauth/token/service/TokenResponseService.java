package com.example.demo.oauth.token.service;

import com.example.demo.oauth.authCode.AuthorizationCode;
import com.example.demo.oauth.token.AccessToken;
import com.example.demo.oauth.token.RefreshToken;
import com.example.demo.oauth.token.TokenResponse;

public interface TokenResponseService {
    TokenResponse consumeAuthCodeAndGenerateTokens(String code) throws Exception;
    TokenResponse<AccessToken, RefreshToken> refreshToken(String refreshToken);
}
