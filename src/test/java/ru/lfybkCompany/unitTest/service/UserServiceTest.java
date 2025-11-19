package ru.lfybkCompany.unitTest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.database.entity.User;
import ru.lfybkCompany.database.repository.UserRepository;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.UserReadDto;
import ru.lfybkCompany.dto.createReadDto.UserUpdateEditDto;
import ru.lfybkCompany.dto.filterDto.UserFilter;
import ru.lfybkCompany.mapper.UserCreateEditMapper;
import ru.lfybkCompany.mapper.UserReadMapper;
import ru.lfybkCompany.mapper.UserUpdateEditMapper;
import ru.lfybkCompany.service.entityService.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserReadMapper userReadMapper;
    @Mock
    private UserCreateEditMapper userCreateEditMapper;
    @Mock
    private UserUpdateEditMapper userUpdateEditMapper;

    private static final Long USER_ID = 1L;

    @InjectMocks
    private UserService userService;

    UserFilter userFilter = new UserFilter("admin",
            "admin",
            LocalDate.parse("2001-07-21"),
            "admin@admin.com",
            Role.ADMIN,
            Gender.MALE);
    List<User> users = List.of(new User(1L, "admin",
            "admin",
            "admin@admin.com",
            LocalDate.parse("2001-07-21"),
            "11",
            Role.ADMIN,
            Gender.MALE));
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
    User user = new User(1L, "admin",
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
    public void test_findAll() {
        List<User> users = List.of(user);

        when(userRepository.findAll()).thenReturn(users);
        when(userReadMapper.map(users.get(0))).thenReturn(userReadDto);

        assertThat(userService.findAll()).hasSize(1);
    }

    @Test
    public void test_findAllByFilter_validData() {
        when(userRepository.findAllByFilter(userFilter)).thenReturn(users);
        when(userReadMapper.map(users.get(0))).thenReturn(userReadDto);

        var userss = userService.findAllByFilter(userFilter);
        assertThat(userss).hasSize(1);
        assertEquals("admin@admin.com", userss.get(0).username());
    }

    @Test
    public void test_findId_validData() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userReadMapper.map(users.get(0))).thenReturn(userReadDto);

        var user = userService.findById(USER_ID);
        user.ifPresent((usera)->assertEquals(USER_ID, usera.id()));
        user.ifPresent((userReadDtoo -> assertEquals(USER_ID, userReadDtoo.id())));
    }

    @Test
    public void test_create_validData() {
        when(passwordEncoder.encode(userCreateEditDto.password())).thenReturn(userCreateEditDto.password());
        when(userCreateEditMapper.map(userCreateEditDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userReadMapper.map(user)).thenReturn(userReadDto);

        var userId = userService.create(userCreateEditDto);
        assertEquals(userId.name(), userCreateEditDto.name());
        assertEquals(userId.lastName(), userCreateEditDto.lastName());
        assertEquals(userId.username(), userCreateEditDto.username());
        assertEquals(userId.birthDate(), userCreateEditDto.birthDate());
        assertEquals(userId.role(), userCreateEditDto.role());
        assertEquals(userId.gender(), userCreateEditDto.gender());

    }

    @Test
    public void test_update_validData() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userUpdateEditMapper.map(userUpdateEditDto, user)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userReadMapper.map(user)).thenReturn(userReadDto);

        var userU = userService.update(USER_ID, userUpdateEditDto);
        userU.ifPresent((userId)-> {
            assertEquals(userId.name(), userUpdateEditDto.name());
            assertEquals(userId.lastName(), userUpdateEditDto.lastName());
            assertEquals(userId.username(), userUpdateEditDto.username());
            assertEquals(userId.birthDate(), userUpdateEditDto.birthDate());
            assertEquals(userId.role(), userUpdateEditDto.role());
            assertEquals(userId.gender(), userUpdateEditDto.gender());
        });
    }

    @Test
    public void test_delete_validAndNotValidData() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        assertTrue(userService.delete(USER_ID));
    }
/*
    @Test
    public void loadUserByUsername() {
        User user = new User(1L, "admin",
                "admin",
                "admin@admin.com",
                null,
                null,
                Role.ADMIN,
                Gender.MALE);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        var username = userService.loadUserByUsername(USERNAME);
        assertEquals(USERNAME, username.getUsername());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "123", authorities = {"ADMIN", "USER"})
    public void getAuthorizationUser() {
        User users = new User(1L, "admin",
                "admin",
                "admin@admin.com",
                null,
                null,
                Role.ADMIN,
                Gender.MALE);
        when(userRepository.findByUsername(users.getUsername())).thenReturn(Optional.of(users));

        var user = userService.getAuthorizationUser();
        user.ifPresent((usera)->assertEquals(USERNAME, usera.username()));
    }


 */

}
