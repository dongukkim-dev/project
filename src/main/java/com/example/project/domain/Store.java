package com.example.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    private String name;
    private String boss; //가게 주인은 음식점을 생성할 때 가져오면 된다.
    private String picture;
    private String content;

    //평점 0 ~ 5까지 리뷰에서 평점을 등록하는데 사람이 많아지면 불러서 계산하는 것도 리소스를 많이 먹나? 안먹으면 평점 삭제 먹으면 평점 유지
    private double rating;

    //여기서 상품 목록이 필요한지 생각해보기(음식점에서 상품목록은 많이 쓰인다)
    
    @Builder
    public Store(String name, String boss, String picture, String content, double rating) {
        this.name = name;
        this.boss = boss;
        this.picture = picture;
        this.content = content;
        this.rating = rating;
    }

    public void update(String name, String picture, String content, double rating) {
        this.name = name;
        this.picture = picture;
        this.content = content;
        this.rating = rating;
    }
}
