package ru.lfybkCompany.mapper;

import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;

@Component
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User>{

    @Override
    public User map(UserCreateEditDto entity) {
        User user = new User();
        copy(user, entity);
        return user;
    }

    @Override
    public User map(UserCreateEditDto entity, User user) {
        copy(user, entity);
        return user;
    }

    private void copy(User user, UserCreateEditDto userDto) {
        user.setName(userDto.name());
        user.setLastName(userDto.lastName());
        user.setUsername(userDto.username());
        user.setBirthDate(userDto.birthDate());
        user.setPassword(userDto.password());
        user.setRole(userDto.role());
        user.setGender(userDto.gender());
    }
}
