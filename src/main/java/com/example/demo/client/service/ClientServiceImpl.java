package com.example.demo.client.service;

import com.example.demo.client.*;
import com.example.demo.converter.ClientConverter;
import com.example.demo.entity.*;
import com.example.demo.oauth.dao.AuthGrantTypeRepository;
import com.example.demo.oauth.dao.AuthMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ClientServiceImpl implements ClientService {
    private InMemoryClientRepository inMemoryClientRepository;
    private JpaClientRepository clientRepository;
    private AuthGrantTypeRepository grantTypeRepository;
    private AuthMethodRepository authMethodRepository;
    private ClientConverter clientConverter;

    @Autowired
    public ClientServiceImpl(@Qualifier("runtimeRepo") InMemoryClientRepository inMemoryClientRepository,
                             @Qualifier("db") JpaClientRepository clientRepository,
                             AuthGrantTypeRepository grantTypeRepository,
                             AuthMethodRepository authMethodRepository, ClientConverter clientConverter) {
        this.inMemoryClientRepository = inMemoryClientRepository;
        this.clientRepository = clientRepository;
        this.grantTypeRepository = grantTypeRepository;
        this.authMethodRepository = authMethodRepository;
        this.clientConverter = clientConverter;
    }

    public ClientServiceImpl() {
    }

    @Override
    public Client saveInMemoryClient(Client client) {
        return inMemoryClientRepository.save(client);
    }


    @Override
    public ClientEntity save(ClientDto clientDto) {
        ClientEntity client = clientConverter.convertFromDto(clientDto);
        clientDto.getGrantTypes().forEach(grantType -> {
            Optional<AuthGrantType> authGrantType =
                    grantTypeRepository.findByGrantType(EnumGrantType.valueOf(
                            grantType.toUpperCase()));
            authGrantType.ifPresent(type -> client.getAuthGrantTypes().add(type));
        });
        Optional<AuthenticationMethod> authMethod =
                authMethodRepository.findByMethodName(EnumAuthMethod.CLIENT_SECRET_BASIC);
        authMethod.ifPresent(method -> client.getAuthMethods().add(authMethod.get()));
        return clientRepository.save(client);
    }

    @Override
    public Optional<? extends BaseClient> loadRegisteredClient(String clientId) {
        Optional<? extends BaseClient> registeredClient = inMemoryClientRepository.findClientById(clientId);
        if (!registeredClient.isPresent()) {
            registeredClient = clientRepository.findByClientId(clientId);
        }
        return registeredClient;
    }

    @Override
    public List<ClientDto> allClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientConverter::convertFromEntity)
                .collect(Collectors.toList());
    }
}
