package com.example.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE store SET deleted = true WHERE store_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String picture;
    private String content;

    private String address;

    private String phone;

    //평점 0 ~ 5까지 리뷰에서 평점을 등록하는데 사람이 많아지면 불러서 계산하는 것도 리소스를 많이 먹나? 안먹으면 평점 삭제 먹으면 평점 유지
    private double rating;

    @ColumnDefault("false")
    private boolean deleted = Boolean.FALSE;

    //상품 판매 상태 [SOLD, ?]
//    private

    //category 임시
    private int category;

    private String openTime;
    private String closeTime;

    //여기서 상품 목록이 필요한지 생각해보기(음식점에서 상품목록은 많이 쓰인다)
    
    @Builder
    public Store(String name, User user, String picture, String content, String address, String phone, double rating, String openTime, String closeTime) {
        this.name = name;
        this.user = user;
        this.picture = picture;
        this.content = content;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void update(String name, String address, String phone, String picture, String content, String openTime, String closeTime) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.picture = picture;
        this.content = content;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void deletedChange() {
        this.deleted = true;
    }
}
