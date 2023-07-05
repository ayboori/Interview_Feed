package com.tenminute.interview_feed.repository;

import com.tenminute.interview_feed.entity.TagPostTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagPostTableRepository extends JpaRepository<TagPostTable, Long> {
}
