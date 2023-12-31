package com.example.project.controller;

import com.example.project.domain.User;
import com.example.project.dto.UpdateUserRequest;
import com.example.project.dto.UserDto;
import com.example.project.dto.login.LoginRequest;
import com.example.project.dto.signup.SignUpRequest;
import com.example.project.repository.user.UserRepository;
import com.example.project.service.LoginService;
import com.example.project.service.UserService;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private final UserRepository userRepository;
    private final LoginService loginService;

    //회원 가입
    @PostMapping("/api/users")
    public ResponseEntity<Long> addUser(@RequestBody @Validated SignUpRequest request) {
        Long savedUser = userService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
    }

    //회원 목록 조회
    @GetMapping("/api/users")
    public ResponseEntity<List<UserDto>> users() {
        List<UserDto> collect = userRepository.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @GetMapping("/api/user")
    public ResponseEntity<UserDto> user() {
        String email = SecurityUtil.getCurrentUsername();

        UserDto user = new UserDto(userService.findByEmail(email));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //회원 수정
    @PutMapping("/api/user")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest request) {
        String email = SecurityUtil.getCurrentUsername();

        User updateUser = userService.update(email, request);

        return ResponseEntity.ok()
                .body(new UserDto(updateUser));
    }
    
    //회원 삭제
    @DeleteMapping("/api/user")
    public ResponseEntity<Void> deleteMember() {
        //삭제 로직
        String email = SecurityUtil.getCurrentUsername();

        userService.delete(email);

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            return new ResponseEntity<>(loginService.login(request), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
