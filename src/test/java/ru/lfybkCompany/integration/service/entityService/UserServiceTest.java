package ru.lfybkCompany.integration.service.entityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
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
    private static final Long USER_ID = 5L;
    private static final String USERNAME = "marina@marinud5.com";

    @Autowired
    private UserService userService;

    private final UserCreateEditDto userCreateEditDto = new UserCreateEditDto(
            "Marina",
            "Marinovna",
            "marina@marinud.com",
            LocalDate.parse("2001-07-21"),
            "{noop}123",
            Role.ADMIN,
            Gender.FEMALE);
    private final UserCreateEditDto userCreateEditDto2 = new UserCreateEditDto(
            "Marina5",
            "Marinovna5",
            "marina@marinud5.com",
            LocalDate.parse("2001-07-26"),
            "{noop}1235",
            Role.ADMIN,
            Gender.FEMALE);
    private final UserUpdateEditDto userUpdateEditDto = new UserUpdateEditDto(
            "Marina5",
            "Marinovna5",
            "marina@marinud5.com",
            LocalDate.parse("2001-07-26"),
            Role.ADMIN,
            Gender.FEMALE);
    private final UserFilter userFilter = new UserFilter(null,
            null,
            null,
            null,
            null,
            Gender.FEMALE);
    private final UserFilter userFilter2 = new UserFilter("Marina5",
            "Marinovna5",
            null,
            "marina@marinud5.com",
            Role.ADMIN,
            Gender.FEMALE);

    @BeforeEach
    public void createUsers() {
        UserCreateEditDto userCreateEditDto1 = new UserCreateEditDto(
                "Marina1",
                "Marinovna1",
                "marina@marinud1.com",
                LocalDate.parse("2001-07-22"),
                "{noop}1231",
                Role.ADMIN,
                Gender.FEMALE);
        UserCreateEditDto userCreateEditDto22 = new UserCreateEditDto(
                "Marina2",
                "Marinovna2",
                "marina@marinud2.com",
                LocalDate.parse("2001-07-23"),
                "{noop}1232",
                Role.ADMIN,
                Gender.FEMALE);
        UserCreateEditDto userCreateEditDto3 = new UserCreateEditDto(
                "Marina3",
                "Marinovna3",
                "marina@marinud3.com",
                LocalDate.parse("2001-07-24"),
                "{noop}1233",
                Role.ADMIN,
                Gender.FEMALE);
        UserCreateEditDto userCreateEditDto4 = new UserCreateEditDto(
                "Marina4",
                "Marinovna4",
                "marina@marinud4.com",
                LocalDate.parse("2001-07-25"),
                "{noop}1234",
                Role.ADMIN,
                Gender.FEMALE);
        userService.create(userCreateEditDto1);
        userService.create(userCreateEditDto22);
        userService.create(userCreateEditDto3);
        userService.create(userCreateEditDto4);
        userService.create(userCreateEditDto2);
    }

    @Test
    public void test_create_validData() {
        var userId = userService.create(userCreateEditDto);
        assertThat(userId.username()).isEqualTo("marina@marinud.com");
        assertEquals(userId.name(), userCreateEditDto.name());
        assertEquals(userId.lastName(), userCreateEditDto.lastName());
        assertEquals(userId.username(), userCreateEditDto.username());
        assertEquals(userId.birthDate(), userCreateEditDto.birthDate());
        assertEquals(userId.role(), userCreateEditDto.role());
        assertEquals(userId.gender(), userCreateEditDto.gender());
    }

    @Test
    public void test_findAll() {
        var users = userService.findAll();
        assertThat(users).hasSize(5);
    }

    @Test
    @WithMockUser(username = "marina@marinud5.com", password = "123", authorities = {"ADMIN", "USER"})//can be most without authorization
    public void test_findAllByFilter_validData() {
        var users = userService.findAllByFilter(userFilter2);
        assertThat(users).hasSize(1);
        assertEquals("marina@marinud5.com", users.get(0).username());
    }

    @Test
    public void test_findId_validData() {
        var user = userService.findById(USER_ID);
        user.ifPresent((userReadDto -> assertEquals(USER_ID, userReadDto.id())));
    }

    @Test
    public void test_update_validData() {
        var userU = userService.update(USER_ID, userUpdateEditDto);
        userU.ifPresent((userId)-> {
            assertThat(userId.username()).isEqualTo("marina@marinud5.com");
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
        var userId = userService.create(userCreateEditDto);
        assertFalse(userService.delete(-2L));
        assertTrue(userService.delete(userId.id()));
    }

    @Test
    public void loadUserByUsername() {
        var username = userService.loadUserByUsername(USERNAME);
        assertEquals(USERNAME, username.getUsername());
    }

    @Test
    @WithMockUser(username = "marina@marinud5.com", password = "123", authorities = {"ADMIN", "USER"})
    public void getAuthorizationUser() {
        var user = userService.getAuthorizationUser();
        user.ifPresent((u)->assertEquals(USERNAME, u.username()));
    }

    @Test
    public void findAllByFilterPageable() {
        Pageable pageable = PageRequest.of(0, 5);
        var users = userService.findAllByFilter(userFilter, pageable);
        assertThat(users).hasSize(5);
    }
}
