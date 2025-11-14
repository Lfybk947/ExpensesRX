package ru.lfybkCompany.mapper;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.dto.createReadDto.UserUpdateEditDto;

@Value
@Component
public class UserUpdateEditMapper implements Mapper<UserUpdateEditDto, User>{

    @Override
    public User map(UserUpdateEditDto entity) {
        User user = new User();
        copy(user, entity);
        return user;
    }

    @Override
    public User map(UserUpdateEditDto entity, User user) {
        copy(user, entity);
        return user;
    }

    private void copy(User user, UserUpdateEditDto userDto) {
        user.setName(userDto.name());
        user.setLastName(userDto.lastName());
        user.setUsername(userDto.username());
        user.setBirthDate(userDto.birthDate());
        user.setRole(userDto.role());
        user.setGender(userDto.gender());
    }
}
