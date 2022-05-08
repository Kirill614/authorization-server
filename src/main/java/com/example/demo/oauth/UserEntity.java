package com.example.demo.oauth;

public class UserEntity {
    private Long id;
    private String username;

    public UserEntity(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserEntity() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
