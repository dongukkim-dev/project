package com.example.project.service;

import com.example.project.domain.Grade;
import com.example.project.domain.Role;
import com.example.project.domain.User;
import com.example.project.dto.UpdateUserRequest;
import com.example.project.dto.signup.SignUpRequest;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long save(SignUpRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        validationDuplicateUser(request);

        return userRepository.save(User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .gender(request.getGender())
                .grade(Grade.BRONZE)
                .point(0)
                .role(request.getRole())
                .build()).getId();
    }

    @Transactional
    public User update(String email, UpdateUserRequest request) {
        User user = findByEmail(email);

        user.updateUser(request.getEmail(), request.getName(), request.getPhone(), request.getAddress());

        return user;
    }

    @Transactional
    public void delete(String email) {
        User user = findByEmail(email);

        user.deletedChange();
    }

    public User findById(Long memberId) {
        return userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected member"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user" + email));
    }

    public List<User> findMembers() {
        return userRepository.findAll();
    }

    private void validationDuplicateUser(SignUpRequest request) {
        //값이 없으면 회원 가입을 진행하고 값이 있으면 중복 예외를 던져야 한다.
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
