package ru.lfybkCompany.database.repository.filterRepository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.lfybkCompany.database.entity.Expenses;
import ru.lfybkCompany.dto.filterDto.ExpensesFilter;

import java.util.List;

public interface FilterExpensesRepository {

    List<Expenses> findAllByFilter(ExpensesFilter filter);
}
