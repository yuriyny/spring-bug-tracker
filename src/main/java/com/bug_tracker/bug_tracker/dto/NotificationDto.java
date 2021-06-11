package com.bug_tracker.bug_tracker.dto;

import com.bug_tracker.bug_tracker.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private Long notificationId;
    private String senderName;
    private Long senderId;
    private String receiverName;
    private Long receiverId;
    private String projectName;
    private Long projectId;
    private Instant date;
    private Role role;
}

