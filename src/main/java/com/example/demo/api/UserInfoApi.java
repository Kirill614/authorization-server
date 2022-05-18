package com.example.demo.api;

import com.example.demo.client.BaseClient;
import com.example.demo.client.service.ClientService;
import com.example.demo.converter.UserInfoMapper;
import com.example.demo.exceptions.RegisteredClientNotFoundException;
import com.example.demo.payload.UserInfoResponse;
import com.example.demo.user.service.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@RestController
@RequestMapping(value = UserInfoApi.URL)
public class UserInfoApi {
    public static final String URL = "/userinfo";
    private ClientService clientService;
    private UserDetailsService userDetailsService;
    private UserInfoMapper userInfoMapper;
    private UserInfoService userInfoService;

    public UserInfoApi(ClientService clientService, UserDetailsService userDetailsService,
                       UserInfoMapper userInfoMapper, UserInfoService userInfoService) {
        this.clientService = clientService;
        this.userDetailsService = userDetailsService;
        this.userInfoMapper = userInfoMapper;
        this.userInfoService = userInfoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<?> getUserInfo(@RequestParam(value = "client_id") String clientId,
                                  @RequestParam("username") String username,
                                  Authentication auth) throws AccessDeniedException {
        if (auth == null) {
            throw new AccessDeniedException("User is not authorized");
        }
        Optional<? extends BaseClient> registeredClient =
                clientService.loadRegisteredClient(clientId);

        if (!registeredClient.isPresent()) {
            throw new RegisteredClientNotFoundException(clientId);
        }
        UserInfoResponse userInfoResponse = userInfoService.getUserInfo(username);
        return ResponseEntity.ok(userInfoResponse);
    }
}
