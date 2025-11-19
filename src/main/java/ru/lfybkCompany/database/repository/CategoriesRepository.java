package ru.lfybkCompany.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lfybkCompany.database.entity.Categories;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    List<Categories> findAllByUserId(Long id);
}
