package com.example.project.service;

import com.example.project.config.jwt.TokenProvider;
import com.example.project.domain.Grade;
import com.example.project.domain.User;
import com.example.project.dto.login.LoginRequest;
import com.example.project.dto.login.LoginResult;
import com.example.project.dto.signup.AddUserRequest;
import com.example.project.dto.signup.SignResponse;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long save(AddUserRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        validationDuplicateUser(request);

        return userRepository.save(User.builder()
                .email(request.getEmail())
                .nickname(request.getName())
                .password(encoder.encode(request.getPassword()))
                .gender(request.getGender())
                .grade(Grade.BRONZE)
                .point(0)
                .build()).getId();
    }

    public User findById(Long memberId) {
        return userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected member"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public List<User> findMembers() {
        return userRepository.findAll();
    }

    private void validationDuplicateUser(AddUserRequest request) {
        //값이 없으면 회원 가입을 진행하고 값이 있으면 중복 예외를 던져야 한다.
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
