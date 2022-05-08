package com.example.demo.client;

import java.util.Optional;

public interface InMemoryClientRepository {
    void save(Client client);
    Optional<BaseClient> findClientById(String id);
}
