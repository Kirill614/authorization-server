package com.example.demo.oauth.authCode;

import com.example.demo.oauth.authCode.AuthorizationCode;

import java.util.Set;

public interface AuthorizationCodeService {
    AuthorizationCode generateAndStoreCode(String clientId, String redirectUri, Set<String> scopes);
    AuthorizationCode getAuthorizationCode(String code);
}
