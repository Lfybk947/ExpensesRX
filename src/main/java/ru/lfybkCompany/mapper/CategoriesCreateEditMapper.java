package ru.lfybkCompany.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.Categories;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.database.repository.UserRepository;
import ru.lfybkCompany.dto.createReadDto.CategoriesCreateEditDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoriesCreateEditMapper implements Mapper<CategoriesCreateEditDto, Categories> {
    private final UserRepository userRepository;

    @Override
    public Categories map(CategoriesCreateEditDto entity) {
        return Categories.builder()
                .name(entity.name())
                .user(getUser(entity.userID()))
                .build();
    }

    private User getUser(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
