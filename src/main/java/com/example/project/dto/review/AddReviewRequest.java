package com.example.project.dto.review;

import com.example.project.domain.Item;
import com.example.project.domain.Review;
import com.example.project.domain.Store;
import com.example.project.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewRequest {

    private Long order_id;
    private String title;
    private String picture;
    private String content;
    private List<Item> itemNames;
    private double rating;

    public Review toEntity(Store store, User user) {
        return Review.builder()
                .user(user)
                .store(store)
                .title(title)
                .picture(picture)
                .content(content)
                .rating(rating)
                .build();
    }
}
