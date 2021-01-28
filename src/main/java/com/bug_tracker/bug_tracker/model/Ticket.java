package com.bug_tracker.bug_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long ticketId;
    private Long ticketName;
    private Instant createdDate;
    private Instant updatedDate;
    private Priority priority;
    @ManyToOne(fetch = LAZY)
    private Participant creator;
    @OneToMany(mappedBy = "ticket")
    private List<Comment> comments;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="project_id")
    private Project project;


}
