package com.bug_tracker.bug_tracker.mapper;
import com.bug_tracker.bug_tracker.dto.ProjectDto;
import com.bug_tracker.bug_tracker.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectDto mapProjectToDto(Project project);
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Project mapDtoToProject(ProjectDto projectDto);
}
