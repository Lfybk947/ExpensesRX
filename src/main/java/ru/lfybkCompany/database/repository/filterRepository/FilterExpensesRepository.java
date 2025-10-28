package ru.lfybkCompany.database.repository.filterRepository;

import ru.lfybkCompany.database.entity.Expenses;
import ru.lfybkCompany.dto.filterDto.ExpensesFilter;

import java.util.List;

public interface FilterExpensesRepository {

    List<Expenses> findAllFilter(ExpensesFilter filter);
}
