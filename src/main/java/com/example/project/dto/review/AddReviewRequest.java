package com.example.project.dto.review;

import com.example.project.domain.Item;
import com.example.project.domain.Review;
import com.example.project.domain.Store;
import com.example.project.domain.User;
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

    private Long orderId;
    private String title;
    private String content;
    private List<Item> itemNames;
    private double rating;

    private String picture;
    private MultipartFile file;

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
