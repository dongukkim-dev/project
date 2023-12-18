package com.example.project.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReviewRequest {

    private String content;
    private double rating;
    private String picture;
}
