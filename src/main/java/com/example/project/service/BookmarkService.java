package com.example.project.service;

import com.example.project.domain.Bookmark;
import com.example.project.domain.Store;
import com.example.project.domain.User;
import com.example.project.dto.BookmarkResponse;
import com.example.project.dto.BookmarkStatusResponse;
import com.example.project.repository.BookmarkRepository;
import com.example.project.repository.store.StoreQueryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
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

    //store_id로 해당 음식점 bookmark를 삭제하면 된다.
    public void deleteBookmark(long id, String email) {
        User user = userService.findByEmail(email);
        Store store = storeService.findById(id);

        if (bookmarkRepository.existsBookmarkByUserAndStore(user, store)) {
            Bookmark bookmark = bookmarkRepository.findBookmarkByUserAndStore(user, store);
            bookmarkRepository.delete(bookmark);
        }
    }
}
