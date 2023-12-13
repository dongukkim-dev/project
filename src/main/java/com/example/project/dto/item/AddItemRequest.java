package com.example.project.dto.item;

import com.example.project.domain.Item;
import com.example.project.domain.ItemStatus;
import com.example.project.domain.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

    private String itemName;
    private int price;
    private String picture;
    private String content;

    private transient MultipartFile file;

    public Item toEntity(Store store) {
        return Item.builder()
                .name(itemName)
                .price(price)
                .picture(picture)
                .content(content)
                .store(store)
                .status(ItemStatus.SALE)
                .build();
    }
}
