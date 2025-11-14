package ru.lfybkCompany.dto.filterDto;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpensesSessionFilter {

    @PastOrPresent(message = "{expenses.date.PastOrPresent}")
    private LocalDateTime fromDate;

    @PastOrPresent(message = "{expenses.date.PastOrPresent}")
    private LocalDateTime toDate;

    private BigDecimal fromSum;

    private BigDecimal toSum;

    private Integer currencyOperations;

    private List<Long> categories;

    private List<Long> descriptions;

    private List<Long> users;

}
