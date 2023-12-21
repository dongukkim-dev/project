package com.example.project.dto;

import com.example.project.domain.Gender;
import com.example.project.domain.Grade;
import com.example.project.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDto {

    private String email;
    private String name;
    private String phone;
    private Gender gender;
    private int point;
    private Grade grade;
    private String address;
    private String detail;
    private LocalDateTime createdDate;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.gender = user.getGender();
        this.point = user.getPoint();
        this.grade = user.getGrade();
        this.address = user.getAddress();
        this.detail = user.getDetail();
        this.createdDate = user.getCreatedDate();
    }
}
