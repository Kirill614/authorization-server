package com.example.demo.oauth.token.service;

import com.example.demo.client.BaseClient;
import com.example.demo.client.service.ClientService;
import com.example.demo.entity.EnumGrantType;
import com.example.demo.exceptions.NotAuthorizedClientException;
import com.example.demo.exceptions.NotValidTokenException;
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
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public TokenResponse<AccessToken, RefreshToken> consumeAuthCodeAndGenerateTokens(String code) throws Exception {
        Assert.notNull(code, "code can not be null");
        AuthorizationCode authCode = authCodeService.getAuthorizationCode(code);
        if (authCode == null || authCode.isExpired()) {
            throw new Exception("auth code was not found or expired");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TokenResponse<AccessToken, RefreshToken> tokenResponse = new TokenResponse<>();
        tokenResponse.setAccessToken(tokenService.createAccessToken(username, authCode.getClientId(), Collections.singletonList("read")));
        tokenResponse.setRefreshToken(tokenService.createRefreshToken(username, authCode.getClientId(), Collections.singletonList("read")));
        return tokenResponse;
    }

    @Override
    public TokenResponse<AccessToken, RefreshToken> refreshToken(String refreshToken) {
        BaseClient registeredClient;
        String clientId = JwtUtils.parseClientIdFromToken(refreshToken);
        String username = JwtUtils.parseUsernameFromToken(refreshToken);

        if (clientId == null || username == null) {
            throw new NotValidTokenException();
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
        TokenResponse<AccessToken, RefreshToken> tokenResponse = new TokenResponse<>();

        tokenResponse.setAccessToken(
                tokenService.createAccessToken(username,
                        clientId,
                        Collections.singletonList("read")));

        tokenResponse.setRefreshToken(
                tokenService.createRefreshToken(username,
                        clientId,
                        Collections.singletonList("read")));

        return tokenResponse;
    }
}
