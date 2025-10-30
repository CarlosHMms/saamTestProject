package com.saamaudit.saamTestProject.repositories;

import com.saamaudit.saamTestProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsUserByUsername(String username);

    void deleteByUsername(String username);
}
