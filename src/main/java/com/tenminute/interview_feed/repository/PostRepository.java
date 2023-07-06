package com.tenminute.interview_feed.repository;

import com.tenminute.interview_feed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTagPostTableList_Tag_NameOrderByCreatedAtDesc(String names);
    List<Post> findAllByOrderByCreatedAtDesc();
}