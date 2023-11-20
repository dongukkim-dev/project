package com.example.project.controller;

import com.example.project.config.jwt.TokenProvider;
import com.example.project.domain.Gender;
import com.example.project.domain.Grade;
import com.example.project.domain.User;
import com.example.project.dto.UserDto;
import com.example.project.dto.login.LoginRequest;
import com.example.project.dto.signup.AddUserRequest;
import com.example.project.dto.signup.SignResponse;
import com.example.project.repository.UserRepository;
import com.example.project.service.LoginService;
import com.example.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원가입 api
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final LoginService loginService;

    final Long id = Long.valueOf(1);
    final String email = "test@asd.123";
    final String password = "1234";
    final String name = "kim";
    final Gender gender = Gender.MAN;
    final int point = 0;
    final Grade grade = Grade.BRONZE;

    User user = User.builder()
            .email(email)
            .password(password)
            .nickname(name)
            .gender(gender)
            .point(point)
            .grade(grade)
            .build();

    @PostMapping("/api/users")
    public ResponseEntity<Long> addUser(@RequestBody AddUserRequest request) {
        Long savedUser = userService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @PostMapping("/api/test")
    public String test() {
//        return SecurityUtil.getCurrentUsername() + "hello";
        return "hello";
    }

    @GetMapping("/api/test")
    public String testAPI() {
        log.info("react 에서 test 요청 들어옴");
        return "React, Spring 통신 성공";
    }

    @GetMapping("/api/members")
    public Result members() {
        List<UserDto> collect = userRepository.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/member")
    public Result member(String email) {
        UserDto user = new UserDto(userService.findByEmail("test@asd.123"));
        return new Result(user);
    }

    @Operation(summary = "Join member", description = "회원가입을 시도한다.")
    @PostMapping("/join")
    public String join() {
        log.info("로그인 시도됨");
        userRepository.save(user);
        return user.toString();
    }

    @PostMapping("/login")
    public ResponseEntity<SignResponse> login(@RequestBody LoginRequest request) {

        //여기서 바로 로그인 로직 진행하지 말고 service에서 처리
        return new ResponseEntity<>(loginService.login(request), HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
