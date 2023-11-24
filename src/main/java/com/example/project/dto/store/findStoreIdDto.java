package com.example.project.dto.store;

import com.example.project.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class findStoreIdDto {

    private Long id;

    public findStoreIdDto(Store store) {
        this.id = store.getUser().getId();
    }
}
