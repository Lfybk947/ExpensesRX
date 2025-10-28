package ru.lfybkCompany.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lfybkCompany.database.entity.Descriptions;

@Repository
public interface DescriptionsRepository extends JpaRepository<Descriptions, Long> {
}
