package com.example.demo.oauth.token.service;

import com.example.demo.client.BaseClient;
import com.example.demo.client.service.ClientService;
import com.example.demo.exceptions.InvalidAuthCodeException;
import com.example.demo.exceptions.NotAuthorizedClientException;
import com.example.demo.exceptions.InvalidTokenException;
import com.example.demo.exceptions.RegisteredClientNotFoundException;
import com.example.demo.oauth.authCode.AuthorizationCode;
import com.example.demo.oauth.authCode.AuthorizationCodeService;
import com.example.demo.oauth.token.AccessToken;
import com.example.demo.oauth.token.JwtUtils;
import com.example.demo.oauth.token.RefreshToken;
import com.example.demo.oauth.token.TokenResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenResponseServiceImpl implements TokenResponseService {
    private Map<String, TokenResponse> tokenResponseMap = new HashMap<>();
    private AuthorizationCodeService authCodeService;
    private TokenService tokenService;
    private ClientService clientService;

    public TokenResponseServiceImpl(AuthorizationCodeService authCodeService
            , TokenService tokenService, ClientService clientService) {
        this.authCodeService = authCodeService;
        this.tokenService = tokenService;
        this.clientService = clientService;
    }

    @Override
    public TokenResponse<AccessToken, RefreshToken> consumeAuthCodeAndGenerateTokens(
            String code, Set<String> scopes) throws Exception {
        AuthorizationCode authCode = authCodeService.getAuthorizationCode(code);
        if (authCode == null || authCode.isExpired()) {
            throw new InvalidAuthCodeException();
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TokenResponse<AccessToken, RefreshToken> tokenResponse = new TokenResponse<>();
        tokenResponse.setAccessToken(tokenService.createAccessToken(username,
                authCode.getClientId(), scopes));
        tokenResponse.setRefreshToken(tokenService.createRefreshToken(username,
                authCode.getClientId(), scopes));
        return tokenResponse;
    }

    @Override
    public TokenResponse<AccessToken, RefreshToken> refreshToken(String refreshToken) {
        BaseClient registeredClient;
        String clientId = JwtUtils.parseClientIdFromToken(refreshToken);
        String username = JwtUtils.parseUsernameFromToken(refreshToken);

        if (clientId == null || username == null) {
            throw new InvalidTokenException();
        }
        Optional<? extends BaseClient> registeredClientOptional = clientService.loadRegisteredClient(clientId);
        if (!registeredClientOptional.isPresent()) {
            throw new RegisteredClientNotFoundException(clientId);
        }
        registeredClient = registeredClientOptional.get();

        if (!registeredClient.getStringGrantTypes()
                .contains(AuthorizationGrantType.REFRESH_TOKEN.getValue())) {
            throw new NotAuthorizedClientException("client is not authorized to request refresh token");
        }

        if (!JwtUtils.validateJwt(refreshToken)) {
            throw new InvalidTokenException();
        }
        TokenResponse<AccessToken, RefreshToken> tokenResponse = new TokenResponse<>();

        tokenResponse.setAccessToken(
                tokenService.createAccessToken(username,
                        clientId,
                        registeredClient.getStringScopes()));

        tokenResponse.setRefreshToken(
                tokenService.createRefreshToken(username,
                        clientId,
                        registeredClient.getStringScopes()));

        return tokenResponse;
    }

    @Override
    public TokenResponse<AccessToken, RefreshToken> generateTokens(String clientId, Set<String> scopes) {
        TokenResponse<AccessToken, RefreshToken> tokenResponse = new TokenResponse<>();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        tokenResponse.setAccessToken(
                tokenService.createAccessToken(username,
                        clientId,
                        scopes));

        tokenResponse.setRefreshToken(
                tokenService.createRefreshToken(username,
                        clientId,
                        scopes));

        return tokenResponse;
    }
}
