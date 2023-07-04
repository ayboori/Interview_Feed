package com.tenminute.interview_feed.repository;

import com.tenminute.interview_feed.entity.TagPostTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagPostTableRepository extends JpaRepository<TagPostTable, Long> {
}
