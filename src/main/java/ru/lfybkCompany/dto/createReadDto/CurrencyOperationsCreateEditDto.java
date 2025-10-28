package ru.lfybkCompany.dto.createReadDto;

import jakarta.validation.constraints.NotNull;


public record CurrencyOperationsCreateEditDto(@NotNull(message = "{currencyOperations.name.notNull}") String name) {
}
