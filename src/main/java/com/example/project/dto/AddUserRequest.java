package com.example.project.dto;

import com.example.project.domain.Gender;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AddUserRequest {

    @NotEmpty
    private String email;

    private String name;
    private String password;
    private Gender gender;
}
