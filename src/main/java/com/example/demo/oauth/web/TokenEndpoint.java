package com.example.demo.oauth.web;

import com.example.demo.client.BaseClient;
import com.example.demo.client.service.ClientService;
import com.example.demo.exceptions.*;
import com.example.demo.oauth.authCode.AuthorizationCodeService;
import com.example.demo.oauth.token.TokenResponse;
import com.example.demo.oauth.token.service.TokenResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
//@RequestMapping(value = TokenEndpoint.URL)
public class TokenEndpoint {
    public static final String URL = "/oauth2/token";
    private TokenResponseService tokenResponseService;
    private AuthorizationCodeService authCodeService;
    private ClientService clientService;

    public TokenEndpoint(TokenResponseService service,
                         AuthorizationCodeService authCodeService, ClientService clientService) {
        this.tokenResponseService = service;
        this.authCodeService = authCodeService;
        this.clientService = clientService;
    }

    @GetMapping("oauth2/token")
    ResponseEntity<TokenResponse> token(@RequestParam("code") String code,
                                        @RequestParam("grant_type") String grantType,
                                        @RequestParam("client_secret") String clientSecret,
                                        @RequestParam("client_id") String clientId) throws Exception {

        Optional<? extends BaseClient> optionalBaseClient = clientService.loadRegisteredClient(clientId);
        if (!optionalBaseClient.isPresent()) {
            throw new RegisteredClientNotFoundException(clientId);
        }

        BaseClient registeredClient = optionalBaseClient.get();
        if (!clientSecret.equals(registeredClient.getClientSecret())) {
            throw new BadCredentialsException("client was not authenticated");
        }

        if (grantType.equalsIgnoreCase(AuthorizationGrantType.AUTHORIZATION_CODE.getValue())) {
            if (code == null || code.isEmpty()) {
                throw new InvalidAuthCodeException();
            }
            return ResponseEntity.ok(tokenResponseService.consumeAuthCodeAndGenerateTokens(code,
                    registeredClient.getStringScopes()));

        } else if (grantType.equalsIgnoreCase(AuthorizationGrantType.CLIENT_CREDENTIALS.getValue())) {
            return ResponseEntity.ok(tokenResponseService.generateTokens(
                    clientId, registeredClient.getStringScopes()));
        }
        throw new WrongGrantTypeException();
    }

    @GetMapping("oauth2/token/refresh")
    ResponseEntity<TokenResponse> refresh(@RequestParam("grant_type") String grantType,
                                          @RequestParam("refresh_token") String refreshToken) {

        if (!grantType.equals(AuthorizationGrantType.REFRESH_TOKEN.getValue())) {
            throw new WrongGrantTypeException();
        }
        if (!StringUtils.hasText(refreshToken)) {
            throw new InvalidTokenException();
        }
        return ResponseEntity.ok(tokenResponseService.refreshToken(refreshToken));
    }
}
