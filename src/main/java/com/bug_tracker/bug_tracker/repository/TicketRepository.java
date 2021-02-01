package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Comment;
import com.bug_tracker.bug_tracker.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByComments(Comment comment);

}
