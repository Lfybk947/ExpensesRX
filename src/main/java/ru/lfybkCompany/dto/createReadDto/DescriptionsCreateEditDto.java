package ru.lfybkCompany.dto.createReadDto;

import jakarta.validation.constraints.NotNull;


public record DescriptionsCreateEditDto(
        @NotNull(message = "{descriptions.name.notNull}") String name,
        @NotNull(message = "{descriptions.user.notNull}") Long userID) {
}
