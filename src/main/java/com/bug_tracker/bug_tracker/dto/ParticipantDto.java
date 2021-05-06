package com.bug_tracker.bug_tracker.dto;

import com.bug_tracker.bug_tracker.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private Long participantId;
    private Long userId;
    private String username;
    private String email;
    private Role role;
    private Long projectId;
}
