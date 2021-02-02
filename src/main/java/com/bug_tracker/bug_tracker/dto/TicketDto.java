package com.bug_tracker.bug_tracker.dto;

import com.bug_tracker.bug_tracker.model.Participant;
import com.bug_tracker.bug_tracker.model.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long ticketId;
    private String ticketName;
    private String projectName;
    private String description;
    private Priority priority;
    //private Participant creator;
}
