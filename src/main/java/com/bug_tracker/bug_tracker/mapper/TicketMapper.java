package com.bug_tracker.bug_tracker.mapper;

import com.bug_tracker.bug_tracker.dto.TicketDto;
import com.bug_tracker.bug_tracker.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    //@Mapping(target = "createdDate", expression = "java(ticket.getCreatedDate())")
    @Mapping(target = "projectId", expression = "java(ticket.getProject().getProjectId())")
    @Mapping(target = "projectName", expression = "java(ticket.getProject().getProjectName())")
    @Mapping(target = "assignedParticipant", expression = "java(ticket.getAssignedParticipant().getParticipantId())")
    @Mapping(target = "assignedParticipantName", expression = "java(ticket.getAssignedParticipant().getUser().getUsername())")
    @Mapping(target = "creatorId", expression = "java(ticket.getCreator().getParticipantId())")
    @Mapping(target = "creatorName", expression = "java(ticket.getCreator().getUser().getUsername())")
    TicketDto mapTicketToDto(Ticket ticket);

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "assignedParticipant", ignore = true)
    @Mapping(target = "ticketHistory", ignore = true)
    Ticket mapDtoToTicket(TicketDto ticketDto);
}
