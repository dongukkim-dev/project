package com.example.project.controller;

import com.example.project.config.jwt.TokenProvider;
import com.example.project.domain.Gender;
import com.example.project.domain.Grade;
import com.example.project.domain.User;
import com.example.project.dto.AddUserRequest;
import com.example.project.repository.UserRepository;
import com.example.project.service.TokenService;
import com.example.project.service.UserService;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.Duration;
import java.util.Map;

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
    private final TokenService tokenService;

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
    public ResponseEntity<Long> addUser(@RequestBody AddUserRequest request, Principal principal) {
        Long savedUser = userService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @PostMapping("/test")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }

    @PostMapping("/join")
    public String join() {
        log.info("로그인 시도됨");
        userRepository.save(user);
        return user.toString();
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        log.info("user email = {}", user.get("email"));
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        return tokenProvider.generateToken(member, Duration.ofDays(14));
    }
}
