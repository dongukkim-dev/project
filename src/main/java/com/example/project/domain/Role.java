package com.example.project.domain;

public enum Role {
    ROLE_USER("USER"),
    ROLE_STORE("STORE"),
    ROLE_ADMIN("ADMIN");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
