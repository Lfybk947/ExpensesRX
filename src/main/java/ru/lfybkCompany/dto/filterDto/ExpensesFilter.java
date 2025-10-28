package ru.lfybkCompany.dto.filterDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ExpensesFilter(
             LocalDateTime fromDate,
             LocalDateTime toDate,
             BigDecimal fromSum,
             BigDecimal toSum,
             Integer currencyOperations,
             List<Long> categories,
             List<Long> descriptions,
             List<Long> users) {
}
