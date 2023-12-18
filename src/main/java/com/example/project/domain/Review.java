package com.example.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    //사진 추가
    private String picture;

    private String content;
    private double rating;

    @Builder
    public Review(User user, Store store, Order order, String picture, String content, double rating) {
        this.user = user;
        this.store = store;
        this.order = order;
        this.picture = picture;
        this.content = content;
        this.rating = rating;
    }

    public void update(String content, double rating, String picture) {
        this.content = content;
        this.rating = rating;
        this.picture = picture;
    }
}
