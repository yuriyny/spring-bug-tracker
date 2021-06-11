package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Project;
import com.bug_tracker.bug_tracker.model.Role;
import com.bug_tracker.bug_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectName(String projectName);
    @Query("select p.project from Participant p where p.user = ?1 and  p.role =?2")
    List<Project> findProjectsByUserAndRole(User user, Role role);
    List<Project> findProjectsByCreator(User user);
    //get projects from the last 30 days
    List<Project> findByCreatorAndCreatedDateBetween(User u, Instant startDate, Instant endDate);

}
