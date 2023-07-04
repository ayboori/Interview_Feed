package com.tenminute.interview_feed.repository;

import com.tenminute.interview_feed.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    boolean existsByNameIgnoreCaseAllIgnoreCase(String name);
    Hashtag findByNameIgnoreCase(String name);
}
