package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "scope")
public class Scope {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "scope_value")
    private String scope;

    public Scope() { }

    public Scope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
