package com.example.project.controller;

import com.example.project.domain.User;
import com.example.project.dto.AddUserRequest;
import com.example.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 회원가입 api
 */
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<Long> addUser(@RequestBody AddUserRequest request, Principal principal) {
        Long savedUser = userService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
    }
}
