package com.example.demo.client;

import java.util.Optional;

public interface InMemoryClientRepository {
    Client save(Client client);
    Optional<BaseClient> findClientById(String id);
}
