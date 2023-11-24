package com.example.project.service;

import com.example.project.domain.Store;
import com.example.project.domain.User;
import com.example.project.dto.store.AddStoreRequest;
import com.example.project.dto.store.StoreListViewResponse;
import com.example.project.dto.store.StoreViewResponse;
import com.example.project.dto.store.UpdateStoreRequest;
import com.example.project.repository.StoreRepository;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
    
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public Store save(AddStoreRequest request, String email) {

        //회원과 음식점 주인 계정 테이블을 분리할지 말지 고민중
        return storeRepository.save(request.toEntity(userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found " + email))));
    }

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    public Store findById(long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public Store findByName(String name) {
        return storeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("not found name: " + name));
    }

    public Store findByUser(User user) {
        return storeRepository.findByUser(user)
                .orElse(null);
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
        if (!store.getUser().getName().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
