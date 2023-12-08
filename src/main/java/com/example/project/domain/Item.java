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

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE item SET deleted = true WHERE item_id = ?")
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
    
    //여기에 완판 여부 들어가야 함 [SALE, SOLD]
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ColumnDefault("false")
    private boolean deleted = Boolean.FALSE;

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
    public void update(String name, int price, String picture, String content, String status) {
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.content = content;

        if (status.equals("SALE")) {
            this.status = ItemStatus.SALE;
        }
        else {
            this.status = ItemStatus.SOLD;
        }
    }

    public void deletedChange() {
        this.deleted = true;
    }
}
