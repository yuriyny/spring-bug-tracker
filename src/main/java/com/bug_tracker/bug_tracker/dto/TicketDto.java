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
public class TicketDto {
    private Long ticketId;
    private String ticketName;
    private Long projectId;
    private String projectName;
    private String description;
    private Priority priority;
    private Instant createdDate;
    private Instant updatedDate;
    private Long creatorId;
    private String creatorName;
    private Long assignedParticipant;
    private String assignedParticipantName;
}
