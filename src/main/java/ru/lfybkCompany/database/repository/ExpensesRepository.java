package ru.lfybkCompany.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.lfybkCompany.database.entity.Expenses;
import ru.lfybkCompany.database.repository.filterRepository.FilterExpensesRepository;
import ru.lfybkCompany.dto.filterDto.ExpensesFilter;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long>, FilterExpensesRepository {

    List<Expenses> findAllExpensesByUserId(Long id);

    List<Expenses> findAllByFilter(ExpensesFilter filter);

}
