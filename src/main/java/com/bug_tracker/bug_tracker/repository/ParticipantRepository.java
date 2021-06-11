package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Participant;
import com.bug_tracker.bug_tracker.model.Project;
import com.bug_tracker.bug_tracker.model.Role;
import com.bug_tracker.bug_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByUser(User user);
    List<Participant> findParticipantByUserAndRole(User user, Role role);
    List<Participant> findByProject(Project project);
    Participant findParticipantByUserAndProject(User user, Project project);
    @Query("select participant from Participant participant where participant.project in (select p.project from Participant p where p.user = ?1)")
    List<Participant> findParticipantsByUser(User user);



}
