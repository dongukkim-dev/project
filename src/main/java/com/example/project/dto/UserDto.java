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

    private Long id;
    private String email;
    private String name;
    private Gender gender;
    private int point;
    private Grade grade;
    private LocalDateTime createdDate;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getNickname();
        this.gender = user.getGender();
        this.point = user.getPoint();
        this.grade = user.getGrade();
        //여기서 날짜도 같이 보내고 싶은데 어떻게 보내지
    }
}
