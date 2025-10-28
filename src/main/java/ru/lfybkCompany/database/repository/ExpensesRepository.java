package ru.lfybkCompany.database.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lfybkCompany.database.entity.Expenses;
import ru.lfybkCompany.database.repository.filterRepository.FilterExpensesRepository;
import ru.lfybkCompany.dto.filterDto.ExpensesFilter;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long>, FilterExpensesRepository {

    List<Expenses> findAllExpensesByUserId(Long id);

    @Query(value = "select e from Expenses e " +
            "join User u " +
            "where u.id = :UserId and e.date between :startDate and :endDate")
    List<Expenses> findAllExpensesByUserIdAndUserDateBetween(Long id, LocalDateTime startDate, LocalDateTime endDate);

    List<Expenses> findAllFilter(ExpensesFilter filter);

}
