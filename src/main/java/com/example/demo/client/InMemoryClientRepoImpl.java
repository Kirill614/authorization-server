package com.example.demo.client;

import com.example.demo.exceptions.RegisteredClientNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("runtimeRepo")
public class InMemoryClientRepoImpl implements InMemoryClientRepository {
    private Map<String, Client> clients = new HashMap<>();

    @Override
    public Client save(Client client) {
       Assert.notNull(client, "client cannot be null");
       return clients.put(client.getClientId(), client);
    }

    @Override
    public Optional<BaseClient> findClientById(String id) {
     if(!clients.containsKey(id)){
        return Optional.empty();
     }
     return Optional.of(clients.get(id));
    }
}
