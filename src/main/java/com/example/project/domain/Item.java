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

    //근데 배달 주문에서 재고가 의미가 있나 고민중
    private int stockQuantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id") //외래키 명
    private Store store;

    @Builder
    public Item(String name, int price, String picture, String content, int stockQuantity, Store store) {
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.content = content;
        this.stockQuantity = stockQuantity;
        this.store = store;
    }
}
