package ru.lfybkCompany.database.repository.filterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.dto.filterDto.UserFilter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository{
    private final EntityManager entityManager;

    @Override
    public Page<User> findAllByFilter(UserFilter filter, Pageable pageable) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);

        var user = criteria.from(User.class);
        criteria.select(user);

        List<Predicate> predicate = buildPredicate(filter, cb, user);
        criteria.where(predicate.toArray(Predicate[]::new));
        criteria.orderBy(getOrder(cb, user));

        TypedQuery<User> pageQuery = entityManager.createQuery(criteria);
        pageQuery.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
        pageQuery.setMaxResults(pageable.getPageSize());

        List<User> content = pageQuery.getResultList();

        Long total = getTotalCount(filter);

        return new PageImpl<>(content, pageable, total);
    }

    private Order getOrder(CriteriaBuilder cb, Root<User> user) {
        return cb.asc(user.get("birthDate"));
    }

    private List<Predicate> buildPredicate(UserFilter filter, CriteriaBuilder cb, Root<User> user) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.name() != null && !filter.name().isBlank()) {
            predicates.add(cb.like(user.get("name"), filter.name()));
        }
        if (filter.lastName() != null && !filter.lastName().isBlank()) {
            predicates.add(cb.like(user.get("lastName"), filter.lastName()));
        }
        if (filter.birthDate() != null) {
            predicates.add(cb.lessThan(user.get("birthDate"), filter.birthDate()));
        }
        if (filter.username() != null  && !filter.username().isBlank()) {
            predicates.add(cb.like(user.get("username"), filter.username()));
        }
        if (filter.role() != null) {
            predicates.add(cb.like(user.get("role"), filter.role().name()));
        }
        if (filter.gender() != null) {
            predicates.add(cb.like(user.get("gender"), filter.gender().name()));
        }

        return predicates;
    }

    private Long getTotalCount(UserFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Long.class);

        var user = criteria.from(User.class);
        criteria.select(cb.count(user));

        List<Predicate> predicate = buildPredicate(filter, cb, user);
        criteria.where(predicate.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getSingleResult();
    }

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);

        var user = criteria.from(User.class);
        criteria.select(user);

        List<Predicate> predicate = buildPredicate(filter, cb, user);
        criteria.where(predicate.toArray(Predicate[]::new));
        criteria.orderBy(getOrder(cb, user));


        return entityManager.createQuery(criteria).getResultList();
    }
}
