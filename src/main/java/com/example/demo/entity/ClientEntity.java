package com.example.demo.entity;

import com.example.demo.client.BaseClient;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "client")
public class ClientEntity implements BaseClient {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "clients_auth_methods",
               joinColumns = @JoinColumn(name = "client_id"),
               inverseJoinColumns = @JoinColumn(name = "auth_method_id"))
    private Set<AuthenticationMethod> authMethods = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "clients_grant_types",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "grant_type_id"))
    private Set<AuthGrantType> authGrantTypes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "clients_scopes",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "scope_id"))
    private Set<Scope> scopes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "clients_redirects",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "redirect_id"))
    private Set<RedirectUri> redirectUris = new HashSet<>();

    public ClientEntity() { }

    public ClientEntity(String clientId, String clientSecret, Set<AuthenticationMethod> authMethods,
                        Set<AuthGrantType> authGrantTypes, Set<Scope> scopes,
                        Set<RedirectUri> redirectUris) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authMethods = authMethods;
        this.authGrantTypes = authGrantTypes;
        this.scopes = scopes;
        this.redirectUris = redirectUris;
    }

    @Override
    public Set<String> getStringGrantTypes(){
        return authGrantTypes.stream()
                .map(item -> item.getGrantType().name())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getStringRedirectUris(){
       return redirectUris.stream()
               .map(RedirectUri::getUri)
               .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getStringScopes(){
        return scopes.stream()
                .map(scope -> scope.getScope())
                .collect(Collectors.toSet());
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Set<AuthenticationMethod> getAuthMethods() {
        return authMethods;
    }

    public void setAuthMethods(Set<AuthenticationMethod> authMethods) {
        this.authMethods = authMethods;
    }

    public Set<AuthGrantType> getAuthGrantTypes() {
        return authGrantTypes;
    }

    public void setAuthGrantTypes(Set<AuthGrantType> authGrantTypes) {
        this.authGrantTypes = authGrantTypes;
    }

    public Set<Scope> getScopes() {
        return scopes;
    }

    public void setScopes(Set<Scope> scopes) {
        this.scopes = scopes;
    }

    public Set<RedirectUri> getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(Set<RedirectUri> redirectUris) {
        this.redirectUris = redirectUris;
    }
}
