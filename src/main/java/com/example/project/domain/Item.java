package com.example.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;

    private String picture;
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id") //외래키 명
    private Store store;
    
    //여기에 완판 여부 들어가야 함
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @Builder
    public Item(String name, int price, String picture, String content, Store store, ItemStatus status) {
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.content = content;
        this.store = store;
        this.status = status;
    }

    //아이템 수정
    public void update(String name, int price, String picture, String content, ItemStatus status) {
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.content = content;
        this.status = status;
    }
}
