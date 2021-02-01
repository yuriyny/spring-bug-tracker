package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
