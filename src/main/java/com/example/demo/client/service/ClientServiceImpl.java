package com.example.demo.client.service;

import com.example.demo.client.*;
import com.example.demo.entity.*;
import com.example.demo.oauth.dao.AuthGrantTypeRepository;
import com.example.demo.oauth.dao.AuthMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class ClientServiceImpl implements ClientService {
    private InMemoryClientRepository inMemoryClientRepository;
    private JpaClientRepository clientRepository;
    private AuthGrantTypeRepository grantTypeRepository;
    private AuthMethodRepository authMethodRepository;

    @Autowired
    public ClientServiceImpl(@Qualifier("runtimeRepo") InMemoryClientRepository inMemoryClientRepository,
                             @Qualifier("db") JpaClientRepository clientRepository,
                             AuthGrantTypeRepository grantTypeRepository,
                             AuthMethodRepository authMethodRepository) {
        this.inMemoryClientRepository = inMemoryClientRepository;
        this.clientRepository = clientRepository;
        this.grantTypeRepository = grantTypeRepository;
        this.authMethodRepository = authMethodRepository;
    }

    @Override
    public void saveInMemoryClient(Client client) {
        inMemoryClientRepository.save(client);
    }

    @Override
    public void save(ClientDto client) {
        if (client.getAuthMethods().contains(EnumAuthMethod.CLIENT_SECRET_BASIC.name().toLowerCase()) &&
                client.getGrantTypes().contains(EnumGrantType.AUTHORIZATION_CODE.name().toLowerCase()) &&
                !client.getRedirectUris().isEmpty() && !client.getScopes().isEmpty()) {
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setClientId(client.getClientId());
            clientEntity.setClientSecret(client.getClientSecret());
            Optional<AuthenticationMethod> authMethod =
                    authMethodRepository.findByMethodName(EnumAuthMethod.CLIENT_SECRET_BASIC);
            Optional<AuthGrantType> authGrantType =
                    grantTypeRepository.findByGrantType(EnumGrantType.AUTHORIZATION_CODE);
            client.getScopes().forEach(scope -> clientEntity.getScopes().add(new Scope(scope)));
            client.getRedirectUris().forEach((uri -> clientEntity.getRedirectUris().add(new RedirectUri(uri))));
            if(authMethod.isPresent() && authGrantType.isPresent()){
                clientEntity.getAuthMethods().add(authMethod.get());
                clientEntity.getAuthGrantTypes().add(authGrantType.get());
            }
            clientRepository.save(clientEntity);
        }
    }

    @Override
    public Optional<? extends BaseClient> loadRegisteredClient(String clientId) {
        Optional<? extends BaseClient> registeredClient = inMemoryClientRepository.findClientById(clientId);
        if(!registeredClient.isPresent()){
            registeredClient = clientRepository.findByClientId(clientId);
        }
        return registeredClient;
    }

}
