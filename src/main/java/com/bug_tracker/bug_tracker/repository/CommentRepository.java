package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Comment;
import com.bug_tracker.bug_tracker.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTicket(Ticket ticket);
}
