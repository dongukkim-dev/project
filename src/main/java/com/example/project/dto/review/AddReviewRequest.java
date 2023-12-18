package com.example.project.dto.review;

import com.example.project.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewRequest {

    private String picture;
    private String content;
    private List<Item> itemNames;
    private double rating;

    public Review toEntity(Store store, User user, Order order) {
        return Review.builder()
                .user(user)
                .store(store)
                .order(order)
                .picture(picture)
                .content(content)
                .rating(rating)
                .build();
    }
}
