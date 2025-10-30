package com.saamaudit.saamTestProject.resources.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDTO(
        @NotEmpty String name,
        String description,
        @NotNull String price,
        @NotNull String quantity
) {
}