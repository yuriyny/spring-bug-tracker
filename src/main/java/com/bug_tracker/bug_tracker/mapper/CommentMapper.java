package com.bug_tracker.bug_tracker.mapper;

import com.bug_tracker.bug_tracker.dto.CommentDto;
import com.bug_tracker.bug_tracker.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "ticketId", expression = "java(comment.getTicket().getTicketId())")
    @Mapping(target = "participantName", expression = "java(comment.getAuthor().getUsername())")
    CommentDto mapCommentToDto(Comment comment);
}
