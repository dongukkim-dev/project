package com.example.project.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStoreRequest {

    private String name;
    private String address;
    private String phone;
    private String picture;
    private String content;
    private String openTime;
    private String closeTime;
    private Integer minOrderPrice;
}
