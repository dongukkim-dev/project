package com.example.project.dto.signup;

import com.example.project.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponse {

    private String email;
    private String name;
    private String password;
    private String token;

    public SignResponse(User user) {
        this.email = user.getEmail();
        this.name = user.getNickname();
        this.password = user.getPassword();
    }
}
