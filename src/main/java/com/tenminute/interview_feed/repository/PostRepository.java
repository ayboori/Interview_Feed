package com.tenminute.interview_feed.repository;

import com.tenminute.interview_feed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
}