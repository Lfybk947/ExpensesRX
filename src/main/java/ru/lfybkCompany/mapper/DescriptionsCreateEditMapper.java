package ru.lfybkCompany.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.Descriptions;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.database.repository.UserRepository;
import ru.lfybkCompany.dto.createReadDto.DescriptionsCreateEditDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DescriptionsCreateEditMapper implements Mapper<DescriptionsCreateEditDto, Descriptions>{
    private final UserRepository userRepository;

    @Override
    public Descriptions map(DescriptionsCreateEditDto entity) {
        return Descriptions.builder()
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
