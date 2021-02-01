package com.bug_tracker.bug_tracker.repository;

import com.bug_tracker.bug_tracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
