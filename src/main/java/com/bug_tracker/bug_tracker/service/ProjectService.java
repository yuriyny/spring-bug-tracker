package com.bug_tracker.bug_tracker.service;

import com.bug_tracker.bug_tracker.dto.ParticipantDto;
import com.bug_tracker.bug_tracker.dto.ParticipantRequest;
import com.bug_tracker.bug_tracker.dto.ProjectDto;
import com.bug_tracker.bug_tracker.exceptions.UserNotFoundException;
import com.bug_tracker.bug_tracker.mapper.ParticipantMapper;
import com.bug_tracker.bug_tracker.mapper.ProjectMapper;
import com.bug_tracker.bug_tracker.model.Participant;
import com.bug_tracker.bug_tracker.model.Project;
import com.bug_tracker.bug_tracker.model.Role;
import com.bug_tracker.bug_tracker.model.User;
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

import java.util.List;

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
    public List<ProjectDto> getUserProjects(Long userId) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        return participantRepository.findByUser(u)
                .stream()
                .map(participant -> projectMapper.mapProjectToDto(participant.getProject()))
                .collect(toList());
    }

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

    @Transactional
    public void test(){
        User u = authService.getCurrentUser();
        List<Participant> p = participantRepository.findParticipantByUserAndRole(u,Role.ADMIN);
        for(Participant pp: p){
            System.out.println(pp.getParticipantId());
        }
        List<Long> l = participantRepository.findProjectIdByUserAndRole(u, Role.ADMIN);
        for(Long ll:l){
            System.out.println(ll);
        }

        List<Project> pro = projectRepository.findProjectsByUserAndRole(u, Role.ADMIN);
        for(Project ff: pro){
            System.out.println(ff.getProjectName());
        }
    }

    @Transactional
    public List<ProjectDto> getProjectsForCurrentUser() {
        User u = authService.getCurrentUser();
        return projectRepository.findProjectsByUserAndRole(u, Role.ADMIN)
                .stream()
                .map(project -> projectMapper.mapProjectToDto(project))
                .collect(toList());
    }
}
