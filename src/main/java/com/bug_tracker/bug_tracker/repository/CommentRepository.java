package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
