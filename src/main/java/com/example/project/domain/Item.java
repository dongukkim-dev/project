package com.example.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;

    //근데 배달 주문에서 재고가 의미가 있나 고민중
    private int stockQuantity;

    private LocalDateTime addDate; //등록일

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id") //외래키 명
    private Store store;
}
