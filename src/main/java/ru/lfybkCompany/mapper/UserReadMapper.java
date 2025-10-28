package ru.lfybkCompany.mapper;

import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.dto.createReadDto.UserReadDto;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto>{
    @Override
    public UserReadDto map(User entity) {
        return new UserReadDto(entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getUsername(),
                entity.getBirthDate(),
                entity.getRole(),
                entity.getGender());
    }
}
