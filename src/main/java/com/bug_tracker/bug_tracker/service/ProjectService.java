package com.bug_tracker.bug_tracker.service;

import com.bug_tracker.bug_tracker.dto.ProjectDto;
import com.bug_tracker.bug_tracker.mapper.ProjectMapper;
import com.bug_tracker.bug_tracker.model.Project;
import com.bug_tracker.bug_tracker.model.User;
import com.bug_tracker.bug_tracker.repository.ProjectRepository;
import com.bug_tracker.bug_tracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final AuthService authService;

    @Transactional
    public ProjectDto save(ProjectDto projectDto){
        Project p = projectMapper.mapDtoToProject(projectDto);
        User u = authService.getCurrentUser();
        userRepository.save(u);
        p.setCreator(u);
        Project save = projectRepository.save(p);
        projectDto.setProjectId(save.getProjectId());
        return projectDto;
    }
}
