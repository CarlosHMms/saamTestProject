package com.saamaudit.saamTestProject.entities;

import com.saamaudit.saamTestProject.resources.dto.LoginRequestDTO;
import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true)
    private String username;

    private String name;
    private String password;

    public boolean isLoginCorrect(LoginRequestDTO loginRequestDTO, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequestDTO.password(), this.password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

}
