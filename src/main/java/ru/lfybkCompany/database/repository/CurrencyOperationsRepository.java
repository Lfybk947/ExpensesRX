package ru.lfybkCompany.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lfybkCompany.database.entity.CurrencyOperations;

@Repository
public interface CurrencyOperationsRepository extends JpaRepository<CurrencyOperations, Integer> {
}
