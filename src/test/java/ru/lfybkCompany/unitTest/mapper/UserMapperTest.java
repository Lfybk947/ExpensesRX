package ru.lfybkCompany.unitTest.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.UserReadDto;
import ru.lfybkCompany.dto.createReadDto.UserUpdateEditDto;
import ru.lfybkCompany.mapper.UserCreateEditMapper;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    @InjectMocks
    private UserCreateEditMapper userCreateEditMapper;

    User userValid = new User(1L, "Danil",
            "Tsaregorodtsev",
            "czare2015@yandex.ru",
            LocalDate.parse("2001-07-21"),
            "{noop}123",
            Role.ADMIN,
            Gender.MALE);
    User userNotValid = new User(1L, "Danil",
            "Tsaregorodtsev",
            "czare2015@yandex.ru",
            null,
            null,
            Role.ADMIN,
            Gender.MALE);
    UserReadDto userReadDto = new UserReadDto(1L, "Danil",
            "Tsaregorodtsev",
            "czare2015@yandex.ru",
            null,
            Role.ADMIN,
            Gender.MALE);
    UserCreateEditDto userCreateEditDtoValid = new UserCreateEditDto(
            "Danil",
            "Tsaregorodtsev",
            "czare2015@yandex.ru",
            LocalDate.parse("2001-07-21"),
            "{noop}123",
            Role.ADMIN,
            Gender.MALE
    );
    UserCreateEditDto userCreateEditDtoNotValid = new UserCreateEditDto(
            "Danil",
            "Tsaregorodtsev",
            null,
            LocalDate.parse("2001-07-20"),
            "{noop}123",
            Role.ADMIN,
            Gender.MALE
    );

    UserUpdateEditDto userUpdateEditDto = new UserUpdateEditDto(
            "Danil",
            "Tsaregorodtsev",
            "czare2015@yandex.ru",
            LocalDate.parse("2001-07-21"),
            Role.ADMIN,
            Gender.MALE
    );

    @Test
    public void test_dataMappers() {
        assertEquals(test_userCreateEditMapper(userCreateEditDtoValid).getBirthDate(), userValid.getBirthDate());
    }


    public User test_userCreateEditMapper(UserCreateEditDto userCreateEditDto) {
        return userCreateEditMapper.map(userCreateEditDto);
    }
}
