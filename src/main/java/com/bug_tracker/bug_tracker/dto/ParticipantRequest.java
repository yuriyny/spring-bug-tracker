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
public class ParticipantRequest {
    private Long userId;
    private Long projectId;
    private Role role;
}
