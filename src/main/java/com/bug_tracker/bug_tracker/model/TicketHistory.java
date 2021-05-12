package com.bug_tracker.bug_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TicketHistory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long ticketHistoryId;
    private String ticketName;
    private String description;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    private Instant updatedDate;
    private Priority priority;
    @OneToOne
    private Participant updateParticipant;
    @OneToOne
    private Participant assignedParticipant;
}
