package com.saamaudit.saamTestProject.resources;

import com.saamaudit.saamTestProject.entities.Product;
import com.saamaudit.saamTestProject.resources.dto.ProductRequestDTO;
import com.saamaudit.saamTestProject.resources.dto.ProductResponseDTO;
import com.saamaudit.saamTestProject.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO requestDTO,
            @AuthenticationPrincipal Jwt principal) {

        Long userId = Long.parseLong(principal.getSubject());

        Product newProduct = productService.insert(requestDTO, userId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newProduct.getProductId())
                .toUri();

        return ResponseEntity.created(location).body(ProductResponseDTO.fromEntity(newProduct));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.listAllProducts().stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId) {
        Product product = productService.listProductById(productId);
        return ResponseEntity.ok(ProductResponseDTO.fromEntity(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequestDTO requestDTO,
            @AuthenticationPrincipal Jwt principal) {

        Long userId = Long.parseLong(principal.getSubject());
        Product updatedProduct = productService.update(productId, requestDTO, userId);
        return ResponseEntity.ok(ProductResponseDTO.fromEntity(updatedProduct));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal Jwt principal) {

        Long userId = Long.parseLong(principal.getSubject());
        productService.delete(productId, userId);
        return ResponseEntity.noContent().build();
    }
}