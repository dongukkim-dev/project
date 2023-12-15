package com.example.project.dto.store;

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
    private String address;
    private String phone;
    private String picture;
    private String content;
    private String openTime;
    private String closeTime;
    private Integer minOrderPrice;

    public Store toEntity(User user) {
        return Store.builder()
                .name(name)
                .user(user)
                .address(address)
                .phone(phone)
                .picture(picture)
                .content(content)
                .openTime(openTime)
                .closeTime(closeTime)
                .minOrderPrice(minOrderPrice)
                .build();
    }
}
