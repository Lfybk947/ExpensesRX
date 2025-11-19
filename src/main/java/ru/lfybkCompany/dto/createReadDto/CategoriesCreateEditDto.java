package ru.lfybkCompany.dto.createReadDto;

import jakarta.validation.constraints.NotNull;


public record CategoriesCreateEditDto(
        @NotNull(message = "{categories.name.notNull}") String name,
        @NotNull(message = "{categories.user.notNull}") Long userID) {
}
