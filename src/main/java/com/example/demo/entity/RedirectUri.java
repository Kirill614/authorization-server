package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "redirect_uri")
public class RedirectUri {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "uri")
    private String uri;

    public RedirectUri() { }

    public RedirectUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
