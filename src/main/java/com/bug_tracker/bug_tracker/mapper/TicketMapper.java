package com.bug_tracker.bug_tracker.mapper;

import com.bug_tracker.bug_tracker.dto.TicketDto;
import com.bug_tracker.bug_tracker.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketDto mapTicketToDto(Ticket ticket);
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "project", ignore = true)
    Ticket mapDtoToTicket(TicketDto ticketDto);
}
