package com.example.project.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReviewRequest {

    private String title;
    private String content;
    private double rating;
    private String picture;
}
