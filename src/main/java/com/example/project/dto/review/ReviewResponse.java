package com.example.project.dto.review;

import com.example.project.domain.Review;
import lombok.Getter;

@Getter
public class ReviewResponse {

    private final Long id;
    private final String storeName;
    private final String userName;
    private final String title;
    private final String picture;
    private final String content;
    private final double rating;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.storeName = review.getStore().getName();
        this.userName = review.getUser().getName();
        this.title = review.getTitle();
        this.picture = review.getPicture();
        this.content = review.getContent();
        this.rating = review.getRating();
    }
}
