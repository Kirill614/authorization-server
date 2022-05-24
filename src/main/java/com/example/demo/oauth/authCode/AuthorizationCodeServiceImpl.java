package com.example.demo.oauth.authCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.*;
import java.util.function.Supplier;

@Service
public class AuthorizationCodeServiceImpl implements AuthorizationCodeService {
    private StringKeyGenerator defaultGenerator =
            new Base64StringKeyGenerator(Base64.getUrlEncoder().withoutPadding(), 96);

    private Supplier<String> codeGenerator = () -> defaultGenerator.generateKey();
    private Map<String, AuthorizationCode> authCodes = new HashMap<String, AuthorizationCode>();
    private static final long authCodeExpiration = 300000;
    private Logger logger = LoggerFactory.getLogger(AuthorizationCodeServiceImpl.class);

    @Override
    public AuthorizationCode generateAndStoreCode(String clientId, String redirectUri, Set<String> scopes){
      String code = codeGenerator.get();
      Date issuedAt = new Date();
      Date expiredAt = new Date(issuedAt.getTime() + authCodeExpiration);
      AuthorizationCode authCode = new AuthorizationCode();
      authCode.setClientId(clientId);
      authCode.setRedirectUri(redirectUri);
      authCode.setIssuedAt(issuedAt);
      authCode.setExpiredAt(expiredAt);
      authCode.setCode(code);
      authCode.setScopes(scopes);
      authCodes.put(code, authCode);
      logger.info("AUTH CODE: " + code);
      return authCode;
    }

    @Override
    public AuthorizationCode getAuthorizationCode(String code){
        Assert.notNull(authCodes.get(code), "code was not found");
        AuthorizationCode authorizationCode = authCodes.get(code);
        authCodes.remove(code);
        return authorizationCode;
    }
}
