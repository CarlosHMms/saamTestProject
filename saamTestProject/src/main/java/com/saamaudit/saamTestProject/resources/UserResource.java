package com.saamaudit.saamTestProject.resources;

import com.saamaudit.saamTestProject.entities.User;
import com.saamaudit.saamTestProject.repositories.UserRepository;
import com.saamaudit.saamTestProject.resources.dto.UserResponseDTO;
import com.saamaudit.saamTestProject.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAllUsers(@AuthenticationPrincipal Jwt principal) {
        List<UserResponseDTO> users = userService.listAllUsers().stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe(@AuthenticationPrincipal Jwt principal) {
        Long userId = Long.parseLong(principal.getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String username,
            @AuthenticationPrincipal Jwt principal) {

        Long authenticatedUserId = Long.parseLong(principal.getSubject());
        userService.deleteUserAccount(username, authenticatedUserId);

        return ResponseEntity.noContent().build();
    }
}