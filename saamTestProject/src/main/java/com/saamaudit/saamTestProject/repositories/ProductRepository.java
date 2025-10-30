package com.saamaudit.saamTestProject.repositories;

import com.saamaudit.saamTestProject.entities.Product;
import com.saamaudit.saamTestProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByUser(User user);
}
