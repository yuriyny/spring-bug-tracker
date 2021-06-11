package com.bug_tracker.bug_tracker.mapper;

import com.bug_tracker.bug_tracker.dto.ProjectDto;
import com.bug_tracker.bug_tracker.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(target = "creator", expression = "java(project.getCreator().getUsername())")
    ProjectDto mapProjectToDto(Project project);
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Project mapDtoToProject(ProjectDto projectDto);
}
