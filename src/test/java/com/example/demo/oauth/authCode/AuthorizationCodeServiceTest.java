package com.example.demo.oauth.authCode;

import com.example.demo.oauth.authCode.AuthorizationCode;
import com.example.demo.oauth.authCode.AuthorizationCodeServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@ExtendWith(MockitoExtension.class)
public class AuthorizationCodeServiceTest {
    private static final String CLIENT_ID = "client";
    private static final String REDIRECT_URI = "http://";
    private static final String CODE = "randomCode";

    @Mock
    private Supplier<String> codeGenerator;

    @Mock
    private Map<String, AuthorizationCode> authCodes;

    @InjectMocks
    private AuthorizationCodeServiceImpl authorizationCodeService;

    @Test
    public void should_generate_and_store_authorization_code(){
        //given
        when(codeGenerator.get()).thenReturn(CODE);
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setCode(CODE);
        authorizationCode.setClientId(CLIENT_ID);
        authorizationCode.setRedirectUri(REDIRECT_URI);

        //when
        AuthorizationCode savedAuthCode = authorizationCodeService
                .generateAndStoreCode(CLIENT_ID, REDIRECT_URI, null);

        //then
        assertEquals(authorizationCode.getCode(), savedAuthCode.getCode());
        assertEquals(authorizationCode.getClientId(), savedAuthCode.getClientId());
        assertEquals(authorizationCode.getRedirectUri(), savedAuthCode.getRedirectUri());
        Mockito.verify(authCodes, times(1)).put(any(), any());
    }

    @Test
    public void should_return_authorization_code(){
        //given
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setCode(CODE);
        when(authCodes.get(CODE)).thenReturn(authorizationCode);

        //when
        AuthorizationCode expectedAuthCode = authorizationCodeService.getAuthorizationCode(CODE);

        //then
        Assertions.assertEquals(authorizationCode, expectedAuthCode);
        verify(authCodes, times(2)).get(CODE);
        verify(authCodes, times(1)).remove(CODE);
    }
}
