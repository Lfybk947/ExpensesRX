package ru.lfybkCompany.dto.createReadDto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpensesReadDto(
        Long id,
        LocalDateTime date,
        BigDecimal sum,
        CurrencyOperationsReadDto currencyOperations,
        CategoriesReadDto categories,
        DescriptionsReadDto descriptions,
        UserReadDto user) {
}
