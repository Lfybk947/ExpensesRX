package ru.lfybkCompany.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.CurrencyOperations;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.database.repository.UserRepository;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsCreateEditDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrencyOperationsCreateEditMapper implements Mapper<CurrencyOperationsCreateEditDto,
                                                                    CurrencyOperations>{
    private final UserRepository userRepository;

    @Override
    public CurrencyOperations map(CurrencyOperationsCreateEditDto entity) {
        return CurrencyOperations.builder()
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
