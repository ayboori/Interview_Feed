package com.tenminute.interview_feed.repository;

import com.tenminute.interview_feed.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
