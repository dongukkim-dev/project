package com.example.project.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * BaseTimeEntity로 가장 최근에 사용된 주소를 나열할 수 있을지도 아니면 새로 필드를 만들기
 */
@Entity
@Getter
@NoArgsConstructor
public class Address extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String address;
    private String detail;

    @Builder
    public Address(String address, String detail) {
        this.address = address;
        this.detail = detail;
    }

    public void update(String detail) {
        this.detail = detail;
    }
}
