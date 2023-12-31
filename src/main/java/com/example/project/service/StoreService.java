package com.example.project.service;

import com.example.project.config.fileupload.FileUpload;
import com.example.project.domain.Category;
import com.example.project.domain.Store;
import com.example.project.domain.User;
import com.example.project.dto.store.AddStoreRequest;
import com.example.project.dto.store.StoreViewResponse;
import com.example.project.dto.store.UpdateStoreRequest;
import com.example.project.repository.BookmarkRepository;
import com.example.project.repository.CategoryRepository;
import com.example.project.repository.store.StoreRepository;
import com.example.project.repository.user.UserRepository;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
    
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FileUpload fileUpload;

    @Transactional
    public Store save(AddStoreRequest request, MultipartFile file, String email) {

        if (file == null) {
            request.setPicture("");
        }
        else {
            if (!fileUpload.uploadStoreImg(request, file))
                throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        //category를 불러오는 코드
        Category category = categoryRepository.findByName(request.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("not found : " + request.getCategory()));

        //회원과 음식점 주인 계정 테이블을 분리할지 말지 고민중
        return storeRepository.save(request.toEntity(userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found " + email)), category));
    }

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    public Store findById(long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public Store findByUser(User user) {
        return storeRepository.findByUser(user)
                .orElse(null);
    }

    @Transactional
    public void delete(long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeStoreAuthor(store);
        store.deletedChange();
    }

    @Transactional
    public Store update(long id, MultipartFile file, UpdateStoreRequest request) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeStoreAuthor(store);

        if (file == null) {
            request.setPicture(store.getPicture());
        }
        else {
            if (!fileUpload.uploadStoreImg(request, file))
                throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        store.update(request.getName(), request.getAddress(), request.getDetail(), request.getPhone(), request.getPicture(), request.getContent(), request.getOpenTime(), request.getCloseTime(), request.getMinOrderPrice());

        return store;
    }

    //음식점을 추가한 유저인지 확인
    private static void authorizeStoreAuthor(Store store) {
        String email = SecurityUtil.getCurrentUsername();
        if (!store.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
