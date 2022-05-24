package com.example.demo.client;

import com.example.demo.client.service.ClientService;
import com.example.demo.client.service.ClientServiceImpl;
import com.example.demo.converter.ClientConverter;
import com.example.demo.entity.*;
import com.example.demo.oauth.dao.AuthGrantTypeRepository;
import com.example.demo.oauth.dao.AuthMethodRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private InMemoryClientRepository inMemoryClientRepository;

    @Mock
    private JpaClientRepository clientRepository;

    @Mock
    private AuthGrantTypeRepository grantTypeRepository;

    @Mock
    private AuthMethodRepository authMethodRepository;

    @Mock
    private ClientConverter clientConverter;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientEntity clientEntity;

    @BeforeEach
    public void setup() {
        clientEntity = new ClientEntity();
        clientEntity.setClientId("client");
        clientEntity.setClientSecret("secret");
        clientEntity.setScopes(new HashSet<>());
        clientEntity.setRedirectUris(new HashSet<>());
    }

    @Test
    public void should_save_client_in_database() {
        //given
        when(clientConverter.convertFromDto(any())).thenReturn(clientEntity);
        when(grantTypeRepository.findByGrantType(any()))
                .thenReturn(Optional.of(new AuthGrantType(EnumGrantType.AUTHORIZATION_CODE)));
        when(authMethodRepository.findByMethodName(any()))
                .thenReturn(Optional.of(new AuthenticationMethod(EnumAuthMethod.CLIENT_SECRET_BASIC)));
        when(clientRepository.save(any())).thenReturn(clientEntity);

        ClientDto clientDto = buildClientDto();

        //when
        ClientEntity expectedEntity = clientService.save(clientDto);

        //then
        Assertions.assertEquals(clientEntity, expectedEntity);
        verify(clientRepository, times(1)).save(clientEntity);

    }

    @Test
    public void should_load_registered_client() {
        //given
        String clientId = "client";
        when(inMemoryClientRepository.findClientById(clientId)).thenReturn(Optional.empty());
        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.of(clientEntity));

        //when
        BaseClient loadedClientEntity = clientService.loadRegisteredClient(clientId).get();

        //then
        Assertions.assertEquals(clientEntity, loadedClientEntity);
        verify(inMemoryClientRepository, times(1)).findClientById(clientId);
        verify(clientRepository, times(1)).findByClientId(clientId);
    }

    @Test
    public void should_return_all_registered_clients() {
        //given
        List<ClientEntity> clientEntityList = Stream.generate(ClientEntity::new)
                .limit(1)
                .collect(Collectors.toList());
        when(clientRepository.findAll()).thenReturn(clientEntityList);
        when(clientConverter.convertFromEntity(any())).thenReturn(buildClientDto());
        List<ClientDto> expectedList = new ArrayList<>();
        expectedList.add(buildClientDto());

        //when
        List<ClientDto> clientDtoList = clientService.allClients();

        //then
        Assertions.assertEquals(1, clientDtoList.size());
        verify(clientRepository, times(1)).findAll();
    }

    public ClientDto buildClientDto() {
        ClientDto clientDto = new ClientDto();
        clientDto.setGrantTypes(new HashSet<>(Collections.singletonList("authorization_code")));
        return clientDto;
    }
}
