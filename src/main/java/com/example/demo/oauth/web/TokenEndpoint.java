package com.example.demo.oauth.web;

import com.example.demo.entity.EnumGrantType;
import com.example.demo.exceptions.NotValidTokenException;
import com.example.demo.exceptions.WrongGrantTypeException;
import com.example.demo.oauth.authCode.AuthorizationCode;
import com.example.demo.oauth.authCode.AuthorizationCodeService;
import com.example.demo.oauth.token.AccessToken;
import com.example.demo.oauth.token.TokenResponse;
import com.example.demo.oauth.token.service.TokenResponseService;
import com.nimbusds.oauth2.sdk.GrantType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping(value = TokenEndpoint.URL)
public class TokenEndpoint {
    public static final String URL = "/oauth2/token";
    private TokenResponseService tokenResponseService;
    private AuthorizationCodeService authCodeService;

    public TokenEndpoint(TokenResponseService service,
                         AuthorizationCodeService authCodeService) {
        this.tokenResponseService = service;
        this.authCodeService = authCodeService;
    }

    @GetMapping("oauth2/token")
    ResponseEntity<TokenResponse> token(@RequestParam("code") String code,
                                        @RequestParam("grant_type") String grantType,
                                        @RequestParam("secret") String secret,
                                        @RequestParam("client_id") String clientId) throws Exception {

        if (!grantType.equalsIgnoreCase(AuthorizationGrantType.AUTHORIZATION_CODE.getValue())) {
            throw new WrongGrantTypeException();
        }
        return ResponseEntity.ok(tokenResponseService.consumeAuthCodeAndGenerateTokens(code));
    }

    @GetMapping("oauth2/token/refresh")
    ResponseEntity<TokenResponse> refresh(@RequestParam("grant_type") String grantType,
                                          @RequestParam("refresh_token") String refreshToken) {

         if(!grantType.equals(AuthorizationGrantType.REFRESH_TOKEN.getValue())){
             throw new WrongGrantTypeException();
         }
         if(!StringUtils.hasText(refreshToken)){
             throw new NotValidTokenException();
         }
         return ResponseEntity.ok(tokenResponseService.refreshToken(refreshToken));
    }
}
