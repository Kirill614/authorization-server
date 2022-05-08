package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "auth_grant_type")
public class AuthGrantType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumGrantType grantType;

    public AuthGrantType() { }

    public AuthGrantType(EnumGrantType grantType) {
        this.grantType = grantType;
    }

    public EnumGrantType getGrantType() {
        return grantType;
    }

    public void setGrantType(EnumGrantType grantType) {
        this.grantType = grantType;
    }
}
