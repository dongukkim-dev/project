package com.example.project.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReviewRequest {

    private String title;
    private String content;
    private double rating;

    private String picture;
    private MultipartFile file;
}
