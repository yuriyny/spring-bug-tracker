package com.bug_tracker.bug_tracker.dto;

import com.bug_tracker.bug_tracker.model.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketHistoryDto {
    private Long ticketHistoryId;
    private String ticketName;
    private String description;
    private Long ticket;
    private Instant updatedDate;
    private Priority priority;
    private Long updateParticipant;
    private Long assignedParticipant;
}
