package com.example.demo;

import com.example.demo.client.Client;
import com.example.demo.client.InMemoryClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.*;

@SpringBootApplication
public class AuthorizationServerApplication implements CommandLineRunner {
    @Autowired
    InMemoryClientRepository clientRepo;

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Client client = new Client();
        client.setClientId("client");
        client.setClientSecret("secret");
        client.setAuthMethod(Collections.singleton(ClientAuthenticationMethod.CLIENT_SECRET_BASIC));
        client.setAuthGrantType(new HashSet<AuthorizationGrantType>(
                Arrays.asList(AuthorizationGrantType.AUTHORIZATION_CODE,
                        AuthorizationGrantType.REFRESH_TOKEN)));
        client.setScopes(new HashSet<>(Collections.singleton("openid")));
//        client.setRedirectUris(new HashSet<>(
//                Collections.singleton("http://127.0.0.1:8080/login/oauth2/code/client-kirill")));
        client.setRedirectUris(new HashSet<>(Collections.singleton("https://oidcdebugger.com/debug")));
        clientRepo.save(client);
    }
}
