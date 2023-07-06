package com.tenminute.interview_feed.repository;

import com.tenminute.interview_feed.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByNameIn(List<String> names);
    List<Tag> findByNameIsIn(List<String> name);
    boolean existsByNameIgnoreCaseAllIgnoreCase(String name);
    Tag findByNameIgnoreCase(String name);
}
