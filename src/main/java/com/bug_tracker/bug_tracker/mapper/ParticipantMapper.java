package com.bug_tracker.bug_tracker.mapper;


import com.bug_tracker.bug_tracker.dto.ParticipantDto;
import com.bug_tracker.bug_tracker.model.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    @Mapping(target = "userId", expression = "java(participant.getUser().getUserId())")
    @Mapping(target = "username", expression = "java(participant.getUser().getUsername())")
    @Mapping(target = "email", expression = "java(participant.getUser().getEmail())")
    @Mapping(target = "projectId", expression = "java(participant.getProject().getProjectId())")
    @Mapping(target = "projectName", expression = "java(participant.getProject().getProjectName())")
    ParticipantDto mapParticipantToDto(Participant participant);

}
