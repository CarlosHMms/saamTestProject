package com.saamaudit.saamTestProject.services;

import com.saamaudit.saamTestProject.entities.Product;
import com.saamaudit.saamTestProject.entities.User;
import com.saamaudit.saamTestProject.repositories.ProductRepository;
import com.saamaudit.saamTestProject.repositories.UserRepository;
import com.saamaudit.saamTestProject.resources.dto.ProductRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = {EntityNotFoundException.class})
    public Product insert(ProductRequestDTO requestDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Product newProduct = new Product();
        newProduct.setName(requestDTO.name());
        newProduct.setDescription(requestDTO.description());
        newProduct.setPrice(requestDTO.price());
        newProduct.setQuantity(requestDTO.quantity());
        newProduct.setUser(user);

        return productRepository.save(newProduct);
    }

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public Product listProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + productId));
    }

    @Transactional(rollbackFor = {AccessDeniedException.class})
    public Product update(Long productId, ProductRequestDTO requestDTO, Long userId) {
        Product product = listProductById(productId);

        validateOwnership(product, userId);

        product.setName(requestDTO.name());
        product.setDescription(requestDTO.description());
        product.setPrice(requestDTO.price());
        product.setQuantity(requestDTO.quantity());

        return productRepository.save(product);
    }

    @Transactional(rollbackFor = {AccessDeniedException.class})
    public void delete(Long productId, Long userId) {
        Product product = listProductById(productId);

        validateOwnership(product, userId);

        productRepository.delete(product);
    }

    private void validateOwnership(Product product, Long userId) {
        if (!Objects.equals(product.getUser().getUserId(), userId)) {
            throw new AccessDeniedException("Acesso negado: Você não é o proprietário deste produto.");
        }
    }
}