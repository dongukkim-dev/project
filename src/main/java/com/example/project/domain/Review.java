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
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE review SET deleted = true WHERE review_id = ?")
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

    //사진 추가
    private String picture;

    private String title;
    private String content;
    private double rating;

    @ColumnDefault("false")
    private boolean deleted = Boolean.FALSE;

    @Builder
    public Review(User user, Store store, String picture, String title, String content, double rating) {
        this.user = user;
        this.store = store;
        this.picture = picture;
        this.title = title;
        this.content = content;
        this.rating = rating;
    }

    public void update(String title, String content, double rating, String picture) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.picture = picture;
    }

    public void deletedChange() {
        this.deleted = true;
    }
}
