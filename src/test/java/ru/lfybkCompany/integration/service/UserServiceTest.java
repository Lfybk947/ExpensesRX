package ru.lfybkCompany.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.UserUpdateEditDto;
import ru.lfybkCompany.dto.filterDto.UserFilter;
import ru.lfybkCompany.service.entityService.UserService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@AutoConfigureMockMvc
public class UserServiceTest {
    private static final Long USER_ID = 1L;
    private static final String USERNAME = "czare2015@yandex.ru";

    @Autowired
    private UserService userService;

    @Test
    public void test_findAll() {
        var users = userService.findAll();
        assertThat(users).hasSize(1);
    }

    @Test
    @WithMockUser(username = "czare2015@yandex.ru", password = "123", authorities = {"ADMIN", "USER"})//can be most without authorization
    public void test_findAllByFilter_validData() {
        UserFilter userFilter = new UserFilter("Danil",
                "Tsaregorodtsev",
                null,
                "czare2015@yandex.ru",
                Role.ADMIN,
                Gender.MALE);
        var users = userService.findAllByFilter(userFilter);
        assertThat(users).hasSize(1);
        assertEquals("czare2015@yandex.ru", users.get(0).username());
    }

    @Test
    public void test_findId_validData() {
        var user = userService.findById(USER_ID);
        user.ifPresent((usera)->assertEquals(USER_ID, user.get().id()));
        user.ifPresent((userReadDto -> assertEquals(USER_ID, userReadDto.id())));
    }

    @Test
    public void test_create_validData() {
        UserCreateEditDto user = new UserCreateEditDto(
                "Marina",
                "Marinovna",
                "marina@marinud.com",
                LocalDate.parse("2001-07-21"),
                "{noop}123",
                Role.USER,
                Gender.FEMALE
        );
        var userId = userService.create(user);
        assertThat(userId.username()).isEqualTo("marina@marinud.com");
        assertEquals(userId.name(), user.name());
        assertEquals(userId.lastName(), user.lastName());
        assertEquals(userId.username(), user.username());
        assertEquals(userId.birthDate(), user.birthDate());
        assertEquals(userId.role(), user.role());
        assertEquals(userId.gender(), user.gender());

    }

    @Test
    public void test_update_validData() {
        UserUpdateEditDto user = new UserUpdateEditDto(
                "Marina",
                "Marinovna",
                "marina@marinud.com",
                LocalDate.parse("2001-07-21"),
                Role.USER,
                Gender.FEMALE
        );
        var userU = userService.update(USER_ID, user);
        userU.ifPresent((userId)-> {
            assertThat(userId.username()).isEqualTo("marina@marinud.com");
            assertEquals(userId.name(), user.name());
            assertEquals(userId.lastName(), user.lastName());
            assertEquals(userId.username(), user.username());
            assertEquals(userId.birthDate(), user.birthDate());
            assertEquals(userId.role(), user.role());
            assertEquals(userId.gender(), user.gender());
        });
    }

    @Test
    public void test_delete_validAndNotValidData() {
        UserCreateEditDto user = new UserCreateEditDto(
                "Marina",
                "Marinovna",
                "marina@marinud.com",
                LocalDate.parse("2001-07-21"),
                "{noop}123",
                Role.USER,
                Gender.FEMALE
        );
        var userId = userService.create(user);
        assertFalse(userService.delete(-2L));
        assertTrue(userService.delete(userId.id()));
    }

    @Test
    public void loadUserByUsername() {
        var username = userService.loadUserByUsername(USERNAME);
        assertEquals(USERNAME, username.getUsername());
    }

    @Test
    @WithMockUser(username = "czare2015@yandex.ru", password = "123", authorities = {"ADMIN", "USER"})
    public void getAuthorizationUser() {
        var user = userService.getAuthorizationUser();
        user.ifPresent((usera)->assertEquals(USERNAME, usera.username()));
    }

    @Test
    public void findAllByFilterPageable() {
        UserFilter userFilter = new UserFilter(null,
                null,
                null,
                null,
                null,
                Gender.MALE);
        Pageable pageable = PageRequest.of(0, 5);
        var users = userService.findAllByFilter(userFilter, pageable);
        assertThat(users).hasSize(5);
//        assertEquals("czare2015@yandex.ru", users.get().toList().get(0).username());
    }
}
