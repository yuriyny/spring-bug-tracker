package com.bug_tracker.bug_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long notificationId;
    @OneToOne
    private User sender;
    @OneToOne
    private User receiver;
    @OneToOne
    private Project project;
    private Instant date;
    private Role role;
}
