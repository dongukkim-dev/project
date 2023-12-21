package com.example.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE user_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    private String password;
    private String name;
    private String phone;
    private String address;
    private String detail;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int point; //적립금

    @Enumerated(EnumType.STRING)
    private Grade grade; //일단 등급은 테이블 없이

//    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
//    private List<Address> addresses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @ColumnDefault("false")
    private boolean deleted = Boolean.FALSE;

    @Builder
    public User(String email, String password, String name, String phone, String address, String detail, Gender gender, int point, Grade grade, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.detail = detail;
        this.gender = gender;
        this.point = point;
        this.grade = grade;
        this.role = role;
    }


    //OAuth2 전용 update
    public User update(String name) {
        this.name = name;
        return this;
    }
    
    //일반 업데이트
    public void updateUser(String name, String phone, String address, String detail) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.detail = detail;
    }

    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void deletedChange() {
        this.deleted = true;
    }
}
