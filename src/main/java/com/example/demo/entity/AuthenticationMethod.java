package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "auth_method")
public class AuthenticationMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumAuthMethod methodName;

    public AuthenticationMethod() { }

    public AuthenticationMethod(EnumAuthMethod methodName) {
        this.methodName = methodName;
    }

    public EnumAuthMethod getAuthMethod() {
        return methodName;
    }

    public void setAuthMethod(EnumAuthMethod authMethod) {
        this.methodName = authMethod;
    }
}
