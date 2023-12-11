package com.example.project.service;

import com.example.project.domain.Bookmark;
import com.example.project.domain.Store;
import com.example.project.domain.User;
import com.example.project.dto.BookmarkResponse;
import com.example.project.dto.BookmarkStatusResponse;
import com.example.project.repository.BookmarkRepository;
import com.example.project.repository.store.StoreQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    private final StoreService storeService;
    private final StoreQueryRepository storeQueryRepository;

    //찜목록 추가 코드 store_id 와 email을 이용한 유저 정보를 얻어서 복합키로 저장한다.
    public Bookmark addBookmark(long id, String email) {
        User user = userService.findByEmail(email);
        Store store = storeService.findById(id);

        return bookmarkRepository.save(Bookmark.builder()
                .user(user)
                .store(store)
                .build());
    }

    //storeId에 따른 총 찜목록 개수
    public BookmarkStatusResponse bookmarkStatus(long id, String email) {
        User user = userService.findByEmail(email);
        Store store = storeService.findById(id);

        return storeQueryRepository.bookmarkStatus(user, store);
    }

    public void deleteBookmark(long id) {
        Store store = storeService.findById(id);

        storeService.delete(id);
    }
}
