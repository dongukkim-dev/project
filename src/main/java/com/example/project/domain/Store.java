package com.example.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Store {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    private String name;
    private String picture;
    private String content;
    private LocalDateTime addStore;

    //평점 0 ~ 5까지 리뷰에서 평점을 등록하는데 사람이 많아지면 불러서 계산하는 것도 리소스를 많이 먹나? 안먹으면 평점 삭제 먹으면 평점 유지
    private float rating;
}
