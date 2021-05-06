package com.bug_tracker.bug_tracker.controller;

import com.bug_tracker.bug_tracker.dto.ParticipantDto;
import com.bug_tracker.bug_tracker.dto.ParticipantRequest;
import com.bug_tracker.bug_tracker.dto.ProjectDto;
import com.bug_tracker.bug_tracker.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectDto create(@RequestBody @Valid ProjectDto projectDto) {
        return projectService.save(projectDto);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<ProjectDto>> getAllProjectsForUser(@PathVariable("userId") Long userId) {
        return status(OK)
                .body(projectService.getUserProjects(userId));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<ProjectDto>> getAllProjectsByUsername(@PathVariable("username") String username) {
        return status(OK)
                .body(projectService.getUserProjectsByUsername(username));
    }

    @PostMapping("/add-participant")
    public ParticipantRequest addParticipant(@RequestBody @Valid ParticipantRequest participantRequest) {
        return projectService.addParticipant(participantRequest);
    }

    @GetMapping("/get-participants/{projectId}")
    public ResponseEntity<List<ParticipantDto>> getAllProjectParticipants(@PathVariable("projectId") Long projectId) {
        return status(OK)
                .body(projectService.getProjectParticipants(projectId));
    }

    @GetMapping("/get-project/{projectId}")
    public ProjectDto getProjectById(@PathVariable("projectId") Long projectId) {
        return this.projectService.getProjectById(projectId);
    }




}
