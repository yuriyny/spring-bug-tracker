package com.bug_tracker.bug_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long ticketId;
    private Instant createdDate;
    private String text;
    private String participantName;
}
