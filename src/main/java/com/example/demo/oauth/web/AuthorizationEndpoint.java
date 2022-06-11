package com.example.demo.oauth.web;

import com.example.demo.client.BaseClient;
import com.example.demo.client.Client;
import com.example.demo.client.InMemoryClientRepoImpl;
import com.example.demo.client.JpaClientRepository;
import com.example.demo.client.service.ClientService;
import com.example.demo.client.service.ClientServiceImpl;
import com.example.demo.entity.ClientEntity;
import com.example.demo.entity.EnumGrantType;
import com.example.demo.exceptions.NotAuthorizedClientException;
import com.example.demo.exceptions.RedirectUriDoesnotMatchException;
import com.example.demo.exceptions.RegisteredClientNotFoundException;
import com.example.demo.exceptions.WrongResponseTypeException;
import com.example.demo.oauth.authCode.AuthorizationCode;
import com.example.demo.oauth.authCode.AuthorizationCodeService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(value = AuthorizationEndpoint.URL)
public class AuthorizationEndpoint {
    public static final String URL = "/oauth2";
    private AuthorizationCodeService authCodeService;
    private InMemoryClientRepoImpl clientRepo;
    private JpaClientRepository jpaClientRepository;
    private ClientService clientService;

    public AuthorizationEndpoint(AuthorizationCodeService authCodeService,
                                 InMemoryClientRepoImpl clientRepo, ClientService clientService) {
        this.authCodeService = authCodeService;
        this.clientRepo = clientRepo;
        this.clientService = clientService;
    }

    @GetMapping(value = "/authorize")
    String auth(@RequestParam("response_type") String responseType,
                @RequestParam("client_id") String clientId,
                @RequestParam("redirect_uri") String redirectUri,
                HttpServletRequest request) throws Exception {

        Optional<? extends BaseClient> optionalRegisteredClient = clientService.loadRegisteredClient(clientId);
        if (!optionalRegisteredClient.isPresent()) {
            throw new RegisteredClientNotFoundException(clientId);
        }

        BaseClient registeredClient = optionalRegisteredClient.get();

        if (!registeredClient.getStringGrantTypes()
                .contains(AuthorizationGrantType.AUTHORIZATION_CODE.getValue())) {
            throw new NotAuthorizedClientException(String.format("Client with id %s is" +
                    " not authorized to request auth code", clientId));
        }

        if (!registeredClient.getStringRedirectUris().contains(redirectUri)) {
            throw new RedirectUriDoesnotMatchException(redirectUri);
        }

        if (!responseType.equals("code")) {
            throw new WrongResponseTypeException(responseType);
        }

        AuthorizationCode code = authCodeService.generateAndStoreCode(clientId, redirectUri,
                registeredClient.getStringScopes());

        return "redirect:" + redirectUri + "?code=" + code.getCode();
    }
}
