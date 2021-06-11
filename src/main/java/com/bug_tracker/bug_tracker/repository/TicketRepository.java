package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByProject(Project project);
    @Query("select t from Ticket t where t.project in (select p.project from Participant p where p.user = ?1)")
    List<Ticket> findTicketsByUser(User user);

    @Query("select t from Ticket t where t.project in (select p.project from Participant p where p.user = ?1) and t.assignedParticipant in (select p from Participant p where p.user = ?1)")
    List<Ticket> findAssignedTicketsByUser(User user);

    @Query("select t from Ticket t where t.status=?2 and t.project in (select p.project from Participant p where p.user = ?1)")
    List<Ticket> findTicketsByUserAndStatus(User user, Status status);

    @Query("select t from Ticket t where t.project in (select p.project from Participant p where p.user = ?1) and t.creator in (select p from Participant p where p.user = ?1)")
    List<Ticket> findCreatedTicketsByUser(User user);




}
