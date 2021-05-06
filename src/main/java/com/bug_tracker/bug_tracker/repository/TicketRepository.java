package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Project;
import com.bug_tracker.bug_tracker.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    //Optional<Ticket> findByComments(Comment comment);
    //Optional<Ticket> findByName(String ticketName);
    List<Ticket> findByProject(Project project);

}
