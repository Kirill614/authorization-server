package com.example.demo.api;

import com.example.demo.client.Client;
import com.example.demo.client.ClientDto;
import com.example.demo.client.InMemoryClientRepository;
import com.example.demo.client.service.ClientService;
import com.example.demo.converter.ClientDetailsConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
public class RegClientController {
   // public static final String URL = "/client";

    private InMemoryClientRepository clientRepository;
    private ClientService clientService;
    private ClientDetailsConverter converter;

    public RegClientController(@Qualifier("runtimeRepo") InMemoryClientRepository repository,
                               ClientService service, ClientDetailsConverter converter){
        this.clientRepository = repository;
        this.clientService = service;
        this.converter = converter;
    }

    @PostMapping(value = "/memory_client")
    ResponseEntity<?> regInMemoryClient(@RequestBody ClientDto clientDto){
        Client client = converter.convertFromDto(clientDto);
        clientService.saveInMemoryClient(client);
        return ResponseEntity.ok("client created");
    }

    @PostMapping(value = "/client")
    ResponseEntity<?> regClient(@RequestBody ClientDto clientDto){
         clientService.save(clientDto);
         return ResponseEntity.ok("client has been saved in DB");
    }



//    @GetMapping("/.well-known/openid-configuration")
//    ResponseEntity<?> wellKnown(){
//        return ResponseEntity.ok("{\"issuer\":\"http://auth-server:9000\",\"authorization_endpoint\":\"http://auth-server:9000/oauth2/authorize\",\"token_endpoint\":\"http://auth-server:9000/oauth2/token\",\"token_endpoint_auth_methods_supported\":[\"client_secret_basic\",\"client_secret_post\",\"client_secret_jwt\",\"private_key_jwt\"],\"jwks_uri\":\"http://auth-server:9000/oauth2/jwks\",\"userinfo_endpoint\":\"http://auth-server:9000/userinfo\",\"response_types_supported\":[\"code\"],\"grant_types_supported\":[\"authorization_code\",\"client_credentials\",\"refresh_token\"],\"subject_types_supported\":[\"public\"],\"id_token_signing_alg_values_supported\":[\"RS256\"],\"scopes_supported\":[\"openid\"]}");
//    }
//
//    @GetMapping("/oauth2/jwks")
//    ResponseEntity<?> jwks(){
//        return ResponseEntity.ok("{\"keys\":[{\"kty\":\"RSA\",\"e\":\"AQAB\",\"kid\":\"5dd62ad9-b390-4b88-ba7f-81ac1c854127\",\"n\":\"qD9WjAbIEfIRaMmWhMKujQ41YrDsKZ2KTDacYj8QV5r9D89k502XQp5JXSpK5x3R_8797WJgjSp-uvwao0bEfTUjHx6gKn_3GHMRJBmXeI4Hx0BUfxnnAAoTBs8CB_LNHhaDiKxwiymwsKSIDnESXsdkMs5tVcUW0XwCuKW7z8zKYuqwla01_SVS6WIC7T33EfIb2Yh-jTglQIt-a0Rvhuf5D3Mc6Df98fit20cr8RSQOQQKGvny4OZ1t3eSSvql8HvjdJCkpP2a40D53Z1kSEL-7VhC5mCqMX9jwaK91ZCcICmTDkRI5VkaWXF_VzzZ0eOGmqnfNae6M6sxA5bw-w\"}]}");
//    }

}
