package ru.lfybkCompany.dto.filterDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.lfybkCompany.dto.createReadDto.CategoriesCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.DescriptionsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpensesSessionFilter {

    @PastOrPresent(message = "{expenses.date.PastOrPresent}")
    LocalDateTime fromDate;

    @PastOrPresent(message = "{expenses.date.PastOrPresent}")
    LocalDateTime toDate;

    BigDecimal fromSum;

    BigDecimal toSum;

    @Pattern(regexp = "^[0-9]*", message = "{filter.field.Pattern}")
    Integer currencyOperations;

    @Pattern(regexp = "^[0-9]*", message = "{filter.field.Pattern}")
    List<Long> categories;

    @Pattern(regexp = "^[0-9]*", message = "{filter.field.Pattern}")
    List<Long> descriptions;

    @Pattern(regexp = "^[0-9]*", message = "{filter.field.Pattern}")
    List<Long> users;

}
