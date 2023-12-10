package com.example.project.service;

import com.example.project.domain.Bookmark;
import com.example.project.domain.Store;
import com.example.project.domain.User;
import com.example.project.repository.BookMarkRepository;
import com.example.project.repository.store.StoreQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final UserService userService;
    private final StoreService storeService;

    //찜목록 추가 코드 store_id 와 email을 이용한 유저 정보를 얻어서 복합키로 저장한다.
    public Bookmark addBookmark(long id, String email) {
        User user = userService.findByEmail(email);
        Store store = storeService.findById(id);

        return bookMarkRepository.save(Bookmark.builder()
                .user(user)
                .store(store)
                .build());
    }
}
