package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
