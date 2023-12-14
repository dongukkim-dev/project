package com.example.project.dto.signup;

import com.example.project.domain.Gender;
import com.example.project.domain.Role;
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
    private String address;
    private Gender gender;
    private Role role;

    public User toEntity(User user) {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .phone(this.phone)
                .address(this.address)
                .gender(this.gender)
                .role(this.role)
                .build();
    }
}
