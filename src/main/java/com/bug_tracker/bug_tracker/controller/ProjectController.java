package com.bug_tracker.bug_tracker.controller;

import com.bug_tracker.bug_tracker.dto.NotificationDto;
import com.bug_tracker.bug_tracker.dto.ParticipantDto;
import com.bug_tracker.bug_tracker.dto.ParticipantRequest;
import com.bug_tracker.bug_tracker.dto.ProjectDto;
import com.bug_tracker.bug_tracker.model.Role;
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

    @PostMapping("/update")
    public ProjectDto update(@RequestBody @Valid ProjectDto projectDto) {
        return projectService.update(projectDto);
    }

    @GetMapping("/get-projects")
    public ResponseEntity<List<ProjectDto>> getProjectsForCurrentUser() {
        return status(OK)
                .body(projectService.getProjectsForCurrentUser());
    }

    @GetMapping("/get-recent-projects")
    public ResponseEntity<List<ProjectDto>> getRecentProjectsForCurrentUser() {
        return status(OK)
                .body(projectService.getRecentProjects());
    }

    @GetMapping("/get-own-projects")
    public ResponseEntity<List<ProjectDto>> getOwnProjectsForCurrentUser() {
        return status(OK)
                .body(projectService.getOwnUserProjects());
    }

    @GetMapping("/get-participants-all")
    public ResponseEntity<List<ParticipantDto>> getAllProjectParticipantsCurrentUser() {
        return status(OK)
                .body(projectService.getAllProjectParticipantsCurrentUser());
    }

    @GetMapping("/get-participant/{projectId}")
    public ResponseEntity<ParticipantDto> getCurrentProjectParticipant(@PathVariable("projectId") Long projectId) {
        return status(OK)
                .body(projectService.getCurrentProjectParticipant(projectId));
    }

    @PostMapping("/send-notification")
    public NotificationDto sendNotification(@RequestBody NotificationDto notificationDto){
        return projectService.sendNotification(notificationDto);
    }

    @GetMapping("/get-notifications")
    public ResponseEntity<List<NotificationDto>> getAllNotificationsForCurrentUser() {
        return status(OK)
                .body(projectService.getAllNotificationsForCurrentUser());
    }
    @DeleteMapping("/delete-notification/{id}")
    public boolean deleteNotification(@PathVariable("id") Long id){
        return projectService.removeNotification(id);
    }

    @GetMapping("/get-projects-by-role/{role}")
    public ResponseEntity<List<ProjectDto>> getProjectsByRole(@PathVariable("role") Role role) {
        return status(OK)
                .body(projectService.getProjectsByRole(role));
    }



}
