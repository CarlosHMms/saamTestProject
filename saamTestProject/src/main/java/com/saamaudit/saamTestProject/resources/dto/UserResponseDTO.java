package com.saamaudit.saamTestProject.resources.dto;

import com.saamaudit.saamTestProject.entities.User;

public record UserResponseDTO(
        Long userId,
        String name,
        String username
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getUsername()
        );
    }
}