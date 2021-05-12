package com.bug_tracker.bug_tracker.mapper;

import com.bug_tracker.bug_tracker.dto.TicketHistoryDto;
import com.bug_tracker.bug_tracker.model.TicketHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketHistoryMapper {
    @Mapping(target = "ticket", expression = "java(ticketHistory.getTicket().getTicketId())")
    @Mapping(target = "ticketName", expression = "java(ticketHistory.getTicket().getTicketName())")
    @Mapping(target = "updateParticipant", expression = "java(ticketHistory.getUpdateParticipant().getParticipantId())")
    @Mapping(target = "assignedParticipant", expression = "java(ticketHistory.getAssignedParticipant().getParticipantId())")
    TicketHistoryDto mapTicketHistoryToDto(TicketHistory ticketHistory);

    @Mapping(target = "updateParticipant", ignore = true)
    @Mapping(target = "assignedParticipant", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "ticketName", expression = "java(ticketHistoryDto.getTicketName())")
    TicketHistory mapDtoToTicketHistory(TicketHistoryDto ticketHistoryDto);
}
