package com.saamaudit.saamTestProject.services;

import com.saamaudit.saamTestProject.entities.User;
import com.saamaudit.saamTestProject.repositories.ProductRepository;
import com.saamaudit.saamTestProject.repositories.UserRepository;
import com.saamaudit.saamTestProject.resources.dto.RegisterRequestDTO;
import com.saamaudit.saamTestProject.resources.dto.UpdateUserDTO;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
    }

    public List<User> listAllUsers(){
        return userRepository.findAll();
    }

    public User insert(RegisterRequestDTO userRequest){
        String encodedPass = passwordEncoder.encode(userRequest.password());

        if (userRepository.findByUsername(userRequest.username()).isPresent()){
            throw new BadCredentialsException("Nome de usuário já cadastrado");
        }

        User newUser = new User();
        newUser.setName(userRequest.name());
        newUser.setUsername(userRequest.username());
        newUser.setPassword(encodedPass);

        return userRepository.save(newUser);
    }
    public void update(UpdateUserDTO updateUser) throws BadRequestException {
        if (!userRepository.existsUserByUsername(updateUser.username())){
            throw new BadRequestException("Você não pode atualizar um usuário que não existe");
        }

        User newUser = new User();
        newUser.setName(updateUser.name());
        newUser.setUsername(updateUser.username());
        userRepository.save(newUser);
    }

    @Transactional(rollbackFor = {EntityNotFoundException.class, AccessDeniedException.class})
    public void deleteUserAccount(String usernameToDelete, Long authenticatedUserId) {
        User userToDelete = userRepository.findByUsername(usernameToDelete)
                .orElseThrow(() -> new EntityNotFoundException("Usuário '" + usernameToDelete + "' não encontrado."));

        if (!Objects.equals(userToDelete.getUserId(), authenticatedUserId)) {
            throw new AccessDeniedException("Acesso negado. Você só pode deletar sua própria conta.");
        }

        productRepository.deleteByUser(userToDelete);

        userRepository.delete(userToDelete);
    }
}
