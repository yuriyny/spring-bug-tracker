package com.bug_tracker.bug_tracker.service;

import com.bug_tracker.bug_tracker.dto.NotificationDto;
import com.bug_tracker.bug_tracker.dto.ParticipantDto;
import com.bug_tracker.bug_tracker.dto.ParticipantRequest;
import com.bug_tracker.bug_tracker.dto.ProjectDto;
import com.bug_tracker.bug_tracker.exceptions.UserNotFoundException;
import com.bug_tracker.bug_tracker.mapper.NotificationMapper;
import com.bug_tracker.bug_tracker.mapper.ParticipantMapper;
import com.bug_tracker.bug_tracker.mapper.ProjectMapper;
import com.bug_tracker.bug_tracker.model.*;
import com.bug_tracker.bug_tracker.repository.NotificationRepository;
import com.bug_tracker.bug_tracker.repository.ParticipantRepository;
import com.bug_tracker.bug_tracker.repository.ProjectRepository;
import com.bug_tracker.bug_tracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final AuthService authService;
    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Transactional
    public ProjectDto save(ProjectDto projectDto) {
        Project p = projectMapper.mapDtoToProject(projectDto);
        User u = authService.getCurrentUser();
        p.setCreator(u);
        Project save = projectRepository.save(p);
        Participant creator = new Participant();
        creator.setProject(p);
        creator.setRole(Role.ADMIN);
        creator.setUser(u);
        participantRepository.save(creator);
        projectDto.setProjectId(save.getProjectId());
        projectDto.setCreator(u.getUsername());
        return projectDto;
    }

    @Transactional
    public ParticipantRequest addParticipant(ParticipantRequest participantRequest) {
        User u = userRepository.getOne(participantRequest.getUserId());
        Project project = projectRepository.getOne(participantRequest.getProjectId());
        Participant participant = new Participant();
        participant.setUser(u);
        participant.setProject(project);
        participant.setRole(participantRequest.getRole());
        participantRepository.save(participant);
        return participantRequest;
    }

    @Transactional
    public List<ParticipantDto> getProjectParticipants(Long projectId) {
        Project p = projectRepository.getOne(projectId);
        return participantRepository.findByProject(p)
                .stream()
                .map(participant -> participantMapper.mapParticipantToDto(participant))
                .collect(toList());
    }

    @Transactional
    public ParticipantDto getCurrentProjectParticipant(Long projectId) {
        User u = authService.getCurrentUser();
        Project p = projectRepository.getOne(projectId);
        Participant participant = participantRepository.findParticipantByUserAndProject(u,p);

        return participantMapper.mapParticipantToDto(participant);

    }

    @Transactional
    public List<ProjectDto> getUserProjects(Long userId) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        return participantRepository.findByUser(u)
                .stream()
                .map(participant -> projectMapper.mapProjectToDto(participant.getProject()))
                .collect(toList());
    }
    //getting all projects where current user take part in
    @Transactional
    public List<ProjectDto> getUserProjectsByUsername(String username) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println(auth.getPrincipal());

        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return participantRepository.findByUser(u)
                .stream()
                .map(participant -> projectMapper.mapProjectToDto(participant.getProject()))
                .collect(toList());
    }
    //get projects created by the user
    @Transactional
    public List<ProjectDto> getOwnUserProjects() {
        User user = authService.getCurrentUser();
        return projectRepository.findProjectsByCreator(user)
                .stream()
                .map(project -> projectMapper.mapProjectToDto(project))
                .collect(toList());
    }

    @Transactional
    public ProjectDto getProjectById(Long projectId) {
        return this.projectMapper.mapProjectToDto(this.projectRepository.getOne(projectId));
    }

    @Transactional
    public ProjectDto update(ProjectDto projectDto) {
        Project p = projectRepository.getOne(projectDto.getProjectId());
        p.setProjectName(projectDto.getProjectName());
        p.setProjectDescription(projectDto.getProjectDescription());
        projectRepository.save(p);
        return projectDto;
    }

    //get projects for user with admin role
    @Transactional
    public List<ProjectDto> getProjectsForCurrentUser() {
        User u = authService.getCurrentUser();
        return projectRepository.findProjectsByUserAndRole(u, Role.ADMIN)
                .stream()
                .map(project -> projectMapper.mapProjectToDto(project))
                .collect(toList());
    }

    @Transactional
    public List<ProjectDto> getProjectsByRole(Role role) {
        User u = authService.getCurrentUser();
        return projectRepository.findProjectsByUserAndRole(u, role)
                .stream()
                .map(project -> projectMapper.mapProjectToDto(project))
                .collect(toList());
    }

    @Transactional
    public List<ParticipantDto> getAllProjectParticipantsCurrentUser() {
        User u = authService.getCurrentUser();
        return participantRepository.findParticipantsByUser(u)
                .stream()
                .map(participant -> participantMapper.mapParticipantToDto(participant))
                .collect(toList());
    }
    @Transactional
    public NotificationDto sendNotification(NotificationDto notificationDto) {

        User u = authService.getCurrentUser();
        User r = userRepository.getOne(notificationDto.getReceiverId());
        Project p = projectRepository.getOne(notificationDto.getProjectId());

        if (notificationRepository.findByReceiverAndAndProject(r, p) == null) {
            Notification n = new Notification();
            n.setSender(u);
            User receiver = userRepository.getOne(notificationDto.getReceiverId());
            n.setReceiver(receiver);
            n.setRole(notificationDto.getRole());
            n.setDate(Instant.now());

            n.setProject(p);
            notificationRepository.save(n);
            notificationDto.setNotificationId(n.getNotificationId());
            notificationDto.setRole(n.getRole());
        }
        return notificationDto;
    }
    @Transactional
    public List<NotificationDto> getAllNotificationsForCurrentUser() {
        User u = authService.getCurrentUser();
        return notificationRepository.findByReceiver(u)
                .stream()
                .map(notification -> notificationMapper.mapNotificationToDto(notification))
                .collect(toList());
    }

    @Transactional
    public boolean removeNotification(Long notificationId){
        this.notificationRepository.deleteById(notificationId);
        return true;
    }

    @Transactional
    public List<ProjectDto> getRecentProjects() {
        User u = authService.getCurrentUser();
        Instant startDate = Instant.now();
        Instant endDate = ZonedDateTime.now().minusDays(15).toInstant();
        return projectRepository.findByCreatorAndCreatedDateBetween(u, endDate, startDate)
                .stream()
                .map(project -> projectMapper.mapProjectToDto(project))
                .collect(toList());
    }
}
