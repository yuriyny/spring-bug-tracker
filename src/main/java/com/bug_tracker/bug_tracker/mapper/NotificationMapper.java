package com.bug_tracker.bug_tracker.mapper;

import com.bug_tracker.bug_tracker.dto.NotificationDto;
import com.bug_tracker.bug_tracker.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "senderName", expression = "java(notification.getSender().getUsername())")
    @Mapping(target = "senderId", expression = "java(notification.getSender().getUserId())")
    @Mapping(target = "receiverName", expression = "java(notification.getReceiver().getUsername())")
    @Mapping(target = "receiverId", expression = "java(notification.getReceiver().getUserId())")
    @Mapping(target = "projectName", expression = "java(notification.getProject().getProjectName())")
    @Mapping(target = "projectId", expression = "java(notification.getProject().getProjectId())")
    @Mapping(target = "date", expression = "java(java.time.Instant.now())")
    NotificationDto mapNotificationToDto(Notification notification);
}
