package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Ticket;
import com.bug_tracker.bug_tracker.model.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Long> {
    List<TicketHistory> findByTicket(Ticket ticket);
}
