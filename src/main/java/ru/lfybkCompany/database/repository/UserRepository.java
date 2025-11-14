package ru.lfybkCompany.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.database.repository.filterRepository.FilterUserRepository;
import ru.lfybkCompany.dto.filterDto.UserFilter;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, FilterUserRepository {

    Optional<User> findUserById(Long id);

    Optional<User> findByUsername(String username);

    List<User> findAllByFilter(UserFilter filter);

    Page<User> findAllByFilter(UserFilter filter, Pageable pageable);
}
