package com.example.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * @IdClass 나 @EmbeddedId 를 사용해서 식별관계를 처리해야 한다.
 */
@Entity
@Getter
public class Bookmark {

    @Id @Column(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id @Column(name = "store_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}
