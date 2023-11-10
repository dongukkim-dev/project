package com.example.project.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddUserRequest {

    @NotEmpty
    private String email;

    private String password;
}
