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

//    public RuntimeClientRepo(List<Client> clientList){
//       Assert.notEmpty(clientList, "client list cannot be null");
//       for(Client client : clientList){
//           Assert.notNull(client, "client can not be null");
//           clients.put(client.getClientId(), client);
//       }
//    }

    @Override
    public void save(Client client) {
       Assert.notNull(client, "client cannot be null");
       clients.put(client.getClientId(), client);
    }

    @Override
    public Optional<BaseClient> findClientById(String id) {
       // return Optional.of(clients.get(id));
     if(!clients.containsKey(id)){
        return Optional.empty();
     }
     return Optional.of(clients.get(id));
    }

//    private boolean checkUniqueClientId(Client client, Map<String, Client> clients){
//        clients.forEach((id, c) -> {
//            if(client.getClientId().equals(id));
//        });
//    }
}
