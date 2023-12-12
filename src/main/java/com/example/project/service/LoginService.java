package com.example.project.service;

import com.example.project.config.jwt.TokenProvider;
import com.example.project.domain.User;
import com.example.project.dto.login.LoginRequest;
import com.example.project.dto.signup.SignResponse;
import com.example.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final StoreService storeService;

    public SignResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        Long storeId = 0L;

        if (storeService.findByUser(user) != null) {
            storeId = storeService.findByUser(user).getId();
        }

        return SignResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .token(tokenProvider.generateToken(user, Duration.ofHours(2)))
                .role(user.getRole().name())
                .storeId(storeId)
                .build();
    }
}
