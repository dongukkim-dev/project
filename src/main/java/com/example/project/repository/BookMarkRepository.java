package com.example.project.repository;

import com.example.project.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<Bookmark, Long> {
}
