package com.example.project.dto.store;

import com.example.project.domain.Category;
import com.example.project.domain.Store;
import com.example.project.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddStoreRequest {

    private String name;
    private String category;
    private String address;
    private String detail;
    private String phone;
    private String picture;
    private String content;
    private String openTime;
    private String closeTime;
    private Integer minOrderPrice;

    public Store toEntity(User user, Category category) {
        return Store.builder()
                .name(name)
                .user(user)
                .category(category)
                .address(address)
                .detail(detail)
                .phone(phone)
                .picture(picture)
                .content(content)
                .openTime(openTime)
                .closeTime(closeTime)
                .minOrderPrice(minOrderPrice)
                .build();
    }
}
