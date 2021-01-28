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
public class Project {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long projectId;
    private String projectName;
    @OneToMany(mappedBy = "project")
    private List<Ticket> tickets;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY )
    @JoinColumn(name = "creator_id")
    private User creator;
    @OneToMany(mappedBy = "project")
    private List<Participant> participants;

}
