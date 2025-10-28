package ru.lfybkCompany.mapper;

import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.dto.createReadDto.UserReadDto;

@Component
public class UserCreateReadMapper implements Mapper<UserReadDto, User>{
    @Override
    public User map(UserReadDto userDto) {
        User user = new User();
        user.setId(userDto.id());
        user.setName(userDto.name());
        user.setLastName(userDto.lastName());
        user.setUsername(userDto.username());
        user.setBirthDate(userDto.birthDate());
        user.setRole(userDto.role());
        user.setGender(userDto.gender());
        return user;
    }
}
