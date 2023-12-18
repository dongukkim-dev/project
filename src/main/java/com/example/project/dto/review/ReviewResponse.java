package com.example.project.dto.review;

import com.example.project.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {

    private Long id;
    private String storeName;
    private String userName;
    private String picture;
    private String content;
    private double rating;
    private List<String> itemNames;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.storeName = review.getStore().getName();
        this.userName = review.getUser().getName();
        this.picture = review.getPicture();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.itemNames = review.getOrder().getOrderItems().stream()
                .map(o -> o.getItem().getName())
                .collect(Collectors.toList());
    }
}
