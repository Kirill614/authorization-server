package com.example.demo.api;

import com.example.demo.client.Client;
import com.example.demo.client.ClientDto;
import com.example.demo.client.InMemoryClientRepository;
import com.example.demo.client.service.ClientService;
import com.example.demo.converter.InMemoryClientConverter;
import com.example.demo.entity.ClientEntity;
import com.example.demo.oauth.authCode.AuthorizationCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.config.HateoasConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.ServerSocket;
import java.util.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
        return ResponseEntity.ok(clientService.saveInMemoryClient(client));
    }

    @PostMapping(value = "/register")
    ResponseEntity<?> registerClient(@Valid @RequestBody ClientDto clientDto) {
        clientService.save(clientDto);
        return ResponseEntity.ok(clientService.save(clientDto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> getAllClients() {
        List<ClientDto> clients = clientService.allClients();
        if (clients.size() > 0){
            clients.forEach(client -> {
                Link selfLink = linkTo(ClientApi.class).slash(client.getClientId()).withSelfRel();
                client.add(selfLink);
            });
        }
        Link link = linkTo(ClientApi.class).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(clients, link));
    }
}
