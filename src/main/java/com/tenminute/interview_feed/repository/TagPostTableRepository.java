package com.tenminute.interview_feed.repository;

import com.tenminute.interview_feed.entity.Tag;
import com.tenminute.interview_feed.entity.TagPostTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TagPostTableRepository extends JpaRepository<TagPostTable, Long> {
    List<TagPostTable> findAllByTagInOrderByPost_CreatedAtDesc(Collection<Tag> tags);
    List<TagPostTable> findAllByTagIn(List<Tag> taglist);
}
