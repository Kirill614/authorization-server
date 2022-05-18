package com.example.demo.client.service;

import com.example.demo.client.BaseClient;
import com.example.demo.client.Client;
import com.example.demo.client.ClientDto;
import com.example.demo.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    void saveInMemoryClient(Client client);
    void save(ClientDto client);
    Optional<? extends BaseClient> loadRegisteredClient(String clientId);
    List<ClientDto> allClients();
}
