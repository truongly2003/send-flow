package com.example.sendflow.mapper;

import com.example.sendflow.dto.request.UserRequest;
import com.example.sendflow.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest userRequest);

    void updateUserFromRequest(UserRequest userRequest, @MappingTarget User existingUser);
}
