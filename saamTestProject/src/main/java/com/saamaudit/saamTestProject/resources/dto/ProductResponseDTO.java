package com.saamaudit.saamTestProject.resources.dto;

import com.saamaudit.saamTestProject.entities.Product;

import java.time.Instant;

public record ProductResponseDTO(
        Long productId,
        String name,
        String description,
        String price,
        String quantity,
        String ownerUsername,
        Instant creationTimeStamp
) {
    public static ProductResponseDTO fromEntity(Product product) {
        return new ProductResponseDTO(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getUser().getUsername(),
                product.getCreationTimeStamp()
        );
    }
}