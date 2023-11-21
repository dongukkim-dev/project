package com.example.project.dto.signup;

import com.example.project.domain.Gender;
import com.example.project.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    private String email;

    private String name;

    @NotBlank
    private String password;
    private String phone;
    private Gender gender;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .phone(this.phone)
                .gender(this.gender)
                .build();
    }
}
