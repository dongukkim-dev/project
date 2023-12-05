package com.example.project.repository.store;

import com.example.project.dto.store.StoreSearchCondition;
import com.example.project.dto.store.StoreUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreRepositoryCustom {
    List<StoreUserDto> search(StoreSearchCondition condition);
    Page<StoreUserDto> searchComplex(StoreSearchCondition condition, Pageable pageable);
}
