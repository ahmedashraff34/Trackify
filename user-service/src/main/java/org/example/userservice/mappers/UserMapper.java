package org.example.userservice.mappers;

import org.example.userservice.dtos.UserRequest;
import org.example.userservice.dtos.UserResponse;
import org.example.userservice.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }

    public User toUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .address(userRequest.getAddress())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .phone(userRequest.getPhone())
                .build();
    }

}
