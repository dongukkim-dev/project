package com.example.project.service;

import com.example.project.domain.Bookmark;
import com.example.project.repository.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;

    //찜목록 추가 코드 store_id 와 email을 이용한 유저 정보를 얻어서 복합키로 저장한다.
//    public Bookmark addBookMark(long id, String email) {
//
//    }
}
