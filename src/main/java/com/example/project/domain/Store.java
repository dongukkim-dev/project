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
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private String picture;
    private String content;

    private String address;
    private String detail;

    private String phone;

    @ColumnDefault("false")
    private boolean deleted = Boolean.FALSE;

    private String openTime;
    private String closeTime;
    private int minOrderPrice;

    //여기서 상품 목록이 필요한지 생각해보기(음식점에서 상품목록은 많이 쓰인다)

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();
    
    @Builder
    public Store(String name, User user, Category category, String picture, String content, String address, String detail, String phone, double rating, String openTime, String closeTime, int minOrderPrice) {
        this.name = name;
        this.user = user;
        this.category = category;
        this.picture = picture;
        this.content = content;
        this.address = address;
        this.detail = detail;
        this.phone = phone;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
    }

    public void update(String name, String address, String detail, String phone, String picture, String content, String openTime, String closeTime, int minOrderPrice) {
        this.name = name;
        this.address = address;
        this.detail = detail;
        this.phone = phone;
        this.picture = picture;
        this.content = content;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
    }

    public void deletedChange() {
        this.deleted = true;
    }
}
