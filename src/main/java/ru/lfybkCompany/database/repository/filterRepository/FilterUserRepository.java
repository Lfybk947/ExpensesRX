package ru.lfybkCompany.database.repository.filterRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.dto.filterDto.UserFilter;

import java.util.List;

public interface FilterUserRepository {
    Page<User> findAllByFilter(UserFilter filter, Pageable pageable);

    List<User> findAllByFilter(UserFilter filter);
}
