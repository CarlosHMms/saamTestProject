package com.saamaudit.saamTestProject.services;

import com.saamaudit.saamTestProject.entities.User;
import com.saamaudit.saamTestProject.repositories.UserRepository;
import com.saamaudit.saamTestProject.resources.dto.RegisterRequestDTO;
import com.saamaudit.saamTestProject.resources.dto.UpdateUserDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> listAllUsers(){
        return userRepository.findAll();
    }

    public User insert(RegisterRequestDTO userRequest){
        String encodedPass = passwordEncoder.encode(userRequest.password());

        if (userRepository.findByUsername(userRequest.username()).isPresent()){
            throw new BadCredentialsException("Nome de usuário já cadastrado");
        }

        User user = new User();
        user.setName(userRequest.name());
        user.setUsername(userRequest.username());
        user.setPassword(encodedPass);

        return userRepository.save(user);
    }

    public void update(UpdateUserDTO updateUser) throws BadRequestException {
        if (!userRepository.existsUserByUsername(updateUser.username())){
            throw new BadRequestException("Você não pode atualizar um usuário que não existe");
        }

        User user = new User();
        user.setName(updateUser.name());
        user.setUsername(updateUser.username());
        userRepository.save(user);
    }
    public void delete(UpdateUserDTO updateUser) throws BadRequestException {
        Optional<User> user = userRepository.findByUsername(updateUser.username());

        user.ifPresentOrElse(userRepository::delete, () -> {
            try {
                throw new BadRequestException("Você não pode deletar um usuário que não existe");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        });






    }
}
