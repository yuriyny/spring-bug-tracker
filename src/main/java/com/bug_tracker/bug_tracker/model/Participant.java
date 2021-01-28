package com.bug_tracker.bug_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long participantId;
    @OneToOne(fetch = LAZY)
    private User user;
    private Role role;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="project_id")
    private Project project;
}
