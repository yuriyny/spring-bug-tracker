package com.bug_tracker.bug_tracker.mapper;

import com.bug_tracker.bug_tracker.dto.UserDto;
import com.bug_tracker.bug_tracker.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UserMapper {
    UserDto mapUserToDto(User user);
}
