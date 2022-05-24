package com.example.demo.api;

import com.example.demo.client.Client;
import com.example.demo.client.ClientDto;
import com.example.demo.client.InMemoryClientRepository;
import com.example.demo.client.service.ClientService;
import com.example.demo.converter.InMemoryClientConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = ClientApi.URL)
public class ClientApi {
    public static final String URL = "/api/clients";

    private InMemoryClientRepository clientRepository;
    private ClientService clientService;
    private InMemoryClientConverter converter;

    public ClientApi(@Qualifier("runtimeRepo") InMemoryClientRepository repository,
                     ClientService service, InMemoryClientConverter converter) {
        this.clientRepository = repository;
        this.clientService = service;
        this.converter = converter;
    }

    @PostMapping(value = "/register/inMemory")
    ResponseEntity<?> registerInMemoryClient(@Valid @RequestBody ClientDto clientDto) {
        Client client = converter.convertFromDto(clientDto);
        clientService.saveInMemoryClient(client);
        return ResponseEntity.ok("client created");
    }

    @PostMapping(value = "/register")
    ResponseEntity<?> registerClient(@Valid @RequestBody ClientDto clientDto) {
        clientService.save(clientDto);
        return ResponseEntity.ok("client has been saved in DB");
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> getAllClients() {
        List<ClientDto> clients = clientService.allClients();
        return ResponseEntity.ok(clients);
    }

}
