package com.example.project.repository;

import com.example.project.domain.Bookmark;
import com.example.project.domain.Store;
import com.example.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsBookmarkByUserAndStore(User user, Store store);

    List<Bookmark> findAllByStore(Store store);
}
