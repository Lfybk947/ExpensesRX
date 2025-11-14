package ru.lfybkCompany.database.repository.filterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import ru.lfybkCompany.database.entity.*;
import ru.lfybkCompany.dto.filterDto.ExpensesFilter;
import ru.lfybkCompany.service.entityService.UserService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterExpensesRepositoryImpl implements FilterExpensesRepository{
    private final EntityManager entityManager;
    private final UserService userService;

    @Override
    public List<Expenses> findAllByFilter(ExpensesFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Expenses> criteria = cb.createQuery(Expenses.class);

        Root<Expenses> expenses = criteria.from(Expenses.class);
        criteria.select(expenses);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.fromDate() != null && filter.toDate() == null) {
            predicates.add(cb.greaterThan(expenses.get("date"), filter.fromDate()));
        }
        if (filter.fromDate() == null && filter.toDate() != null) {
            predicates.add(cb.lessThan(expenses.get("date"), filter.toDate()));
        }
        if (filter.fromDate() != null && filter.toDate() != null) {
            predicates.add(cb.between(expenses.get("date"), filter.fromDate(), filter.toDate()));
        }

        if (filter.fromSum() != null && filter.toSum() == null) {
            predicates.add(cb.greaterThan(expenses.get("sum"), filter.fromSum()));
        }
        if (filter.fromSum() == null && filter.toSum() != null) {
            predicates.add(cb.lessThan(expenses.get("sum"), filter.toSum()));
        }
        if (filter.fromSum() != null && filter.toSum() != null) {
            predicates.add(cb.between(expenses.get("sum"), filter.fromSum(), filter.toSum()));
        }

        if (filter.currencyOperations() != null) {
            predicates.add(cb.equal(expenses.get("currencyOperations").get("id"), filter.currencyOperations()));
        }

        if (filter.categories() != null && !filter.categories().isEmpty()) {
            predicates.add(expenses.get("categories").get("id").in(filter.categories()));
        }

        if (filter.descriptions() != null && !filter.descriptions().isEmpty()) {
            predicates.add(expenses.get("descriptions").get("id").in(filter.descriptions()));
        }

        var user = userService.getAuthorizationUser().orElseThrow();

        if (filter.users() != null && !filter.users().isEmpty() && user.role() == Role.ADMIN) {
            predicates.add(expenses.get("user").get("id").in(filter.users()));
        }

        if (user.role() != Role.ADMIN) {
            predicates.add(expenses.get("user").get("id").in(user.id()));
        }

        criteria.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }
}
