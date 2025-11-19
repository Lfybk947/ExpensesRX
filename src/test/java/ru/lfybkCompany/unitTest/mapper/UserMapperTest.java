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
import ru.lfybkCompany.mapper.UserCreateReadMapper;
import ru.lfybkCompany.mapper.UserReadMapper;
import ru.lfybkCompany.mapper.UserUpdateEditMapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    @InjectMocks
    private UserCreateEditMapper userCreateEditMapper;
    @InjectMocks
    private UserCreateReadMapper userCreateReadMapper;
    @InjectMocks
    private UserUpdateEditMapper userUpdateEditMapper;
    @InjectMocks
    private UserReadMapper userReadMapper;

    User user = new User(1L, "admin",
            "admin",
            "admin@admin.com",
            LocalDate.parse("2001-07-21"),
            "11",
            Role.ADMIN,
            Gender.MALE);
    UserReadDto userReadDto = new UserReadDto(1L, "admin",
            "admin",
            "admin@admin.com",
            LocalDate.parse("2001-07-21"),
            Role.ADMIN,
            Gender.MALE);
    UserCreateEditDto userCreateEditDto = new UserCreateEditDto(
            "admin",
            "admin",
            "admin@admin.com",
            LocalDate.parse("2001-07-21"),
            "11",
            Role.ADMIN,
            Gender.MALE);
    UserUpdateEditDto userUpdateEditDto = new UserUpdateEditDto(
            "admin",
            "admin",
            "admin@admin.com",
            LocalDate.parse("2001-07-21"),
            Role.ADMIN,
            Gender.MALE);

    @Test
    public void test_userCreateEditMapper() {
        var dto = userCreateEditMapper.map(userCreateEditDto);
        assertEquals(dto.getBirthDate(), userCreateEditDto.birthDate());
        assertEquals(dto.getName(), userCreateEditDto.name());
        assertEquals(dto.getLastName(), userCreateEditDto.lastName());
        assertEquals(dto.getUsername(), userCreateEditDto.username());
        assertEquals(dto.getRole(), userCreateEditDto.role());
        assertEquals(dto.getGender(), userCreateEditDto.gender());
        assertEquals(dto.getPassword(), userCreateEditDto.password());
    }

    @Test
    public void test_userCreateReadMapper() {
        var dto = userCreateReadMapper.map(userReadDto);
        assertEquals(dto.getId(), userReadDto.id());
        assertEquals(dto.getBirthDate(), userReadDto.birthDate());
        assertEquals(dto.getName(), userReadDto.name());
        assertEquals(dto.getLastName(), userReadDto.lastName());
        assertEquals(dto.getUsername(), userReadDto.username());
        assertEquals(dto.getRole(), userReadDto.role());
        assertEquals(dto.getGender(), userReadDto.gender());
    }

    @Test
    public void test_userUpdateEditMapper() {
        var dto = userUpdateEditMapper.map(userUpdateEditDto);
        assertEquals(dto.getBirthDate(), userUpdateEditDto.birthDate());
        assertEquals(dto.getName(), userUpdateEditDto.name());
        assertEquals(dto.getLastName(), userUpdateEditDto.lastName());
        assertEquals(dto.getUsername(), userUpdateEditDto.username());
        assertEquals(dto.getRole(), userUpdateEditDto.role());
        assertEquals(dto.getGender(), userUpdateEditDto.gender());
    }
    
    @Test
    public void test_userReadMapper() {
        var dto = userReadMapper.map(user);
        assertEquals(dto.id(), user.getId());
        assertEquals(dto.birthDate(), user.getBirthDate());
        assertEquals(dto.name(), user.getName());
        assertEquals(dto.lastName(), user.getLastName());
        assertEquals(dto.username(), user.getUsername());
        assertEquals(dto.role(), user.getRole());
        assertEquals(dto.gender(), user.getGender());
    }

}
