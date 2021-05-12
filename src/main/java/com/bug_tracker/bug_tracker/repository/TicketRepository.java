package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Project;
import com.bug_tracker.bug_tracker.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByProject(Project project);

}
