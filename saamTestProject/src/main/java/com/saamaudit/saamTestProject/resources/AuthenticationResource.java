package com.saamaudit.saamTestProject.resources;

import com.saamaudit.saamTestProject.entities.User;
import com.saamaudit.saamTestProject.repositories.UserRepository;
import com.saamaudit.saamTestProject.resources.dto.LoginRequestDTO;
import com.saamaudit.saamTestProject.resources.dto.LoginResponseDTO;
import com.saamaudit.saamTestProject.resources.dto.RegisterRequestDTO;
import com.saamaudit.saamTestProject.resources.dto.RegisterResponseDTO;
import com.saamaudit.saamTestProject.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/")
public class AuthenticationResource {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthenticationResource(JwtEncoder jwtEncoder, UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        var user = userRepository.findByUsername(loginRequestDTO.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequestDTO, passwordEncoder)){
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("saamBackend")
                .subject(user.get().getUserId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
    }
    @PostMapping("register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO){

        User user = userService.insert(registerRequestDTO);

        return ResponseEntity.ok(new RegisterResponseDTO(user.getUsername()));
    }

}
