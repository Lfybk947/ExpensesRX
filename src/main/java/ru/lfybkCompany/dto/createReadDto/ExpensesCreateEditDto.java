package ru.lfybkCompany.dto.createReadDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;
import ru.lfybkCompany.validator.ExpensesInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@ExpensesInfo
public record ExpensesCreateEditDto(
        @NotBlank(message = "{expenses.date.notBlank}") LocalDateTime date,
        @NotEmpty(message = "{expenses.sum.notEmpty}") BigDecimal sum,
        @Positive(message = "{expenses.currencyOperations.positive}") Integer currencyOperationsID,
        @Positive(message = "{expenses.categories.positive}") Long categoriesID,
        @Positive(message = "{expenses.descriptions.positive}") Long descriptionsID,
        @NotNull(message = "{expenses.user.notNull}") Long userID) {

}
