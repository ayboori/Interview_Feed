package com.tenminute.interview_feed.repository;

import com.tenminute.interview_feed.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByNameIgnoreCaseAllIgnoreCase(String name);
    Tag findByNameIgnoreCase(String name);
}
