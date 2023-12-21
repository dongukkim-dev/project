package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String email; //이메일은 중복확인이 필요함
    private String name;
    private String phone;
    private String address;
    private String detail;
}
