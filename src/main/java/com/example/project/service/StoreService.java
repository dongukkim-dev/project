package com.example.project.service;

import com.example.project.domain.Store;
import com.example.project.dto.store.AddStoreRequest;
import com.example.project.dto.store.StoreListViewResponse;
import com.example.project.dto.store.StoreViewResponse;
import com.example.project.dto.store.UpdateStoreRequest;
import com.example.project.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    
    private final StoreRepository storeRepository;

    public Store save(AddStoreRequest request, String userName) {
        return storeRepository.save(request.toEntity(userName));
    }

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    public Store findById(long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void delete(long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(store);
        storeRepository.delete(store);
    }

    @Transactional
    public Store update(long id, UpdateStoreRequest request) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(store);
        store.update(request.getName(), request.getPicture(), request.getContent(), request.getRating());

        return store;
    }

    public List<StoreViewResponse> findAllDesc() {
        return storeRepository.findAllDesc().stream()
                .map(StoreViewResponse::new)
                .collect(Collectors.toList());
    }

    //음식점을 추가한 유저인지 확인
    private static void authorizeArticleAuthor(Store store) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!store.getBoss().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}