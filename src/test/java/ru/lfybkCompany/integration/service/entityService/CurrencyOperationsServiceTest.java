package ru.lfybkCompany.integration.service.entityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.service.entityService.CurrencyOperationsService;
import ru.lfybkCompany.service.entityService.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@IT
@AutoConfigureMockMvc
public class CurrencyOperationsServiceTest {

    @Autowired
    private CurrencyOperationsService currencyOperationsService;
    @Autowired
    private UserService userService;

    private List<CurrencyOperationsCreateEditDto> createEditDto;
    private static final String NAME = "name";

    @BeforeEach
    public void create() {
        UserCreateEditDto user = new UserCreateEditDto( "1", "1", "admin@admin.com",
                LocalDate.of(1999, 12, 22), "11", Role.ADMIN, Gender.MALE);
        userService.create(user);

        createEditDto = new ArrayList<>();
        createEditDto.add(new CurrencyOperationsCreateEditDto(NAME, 1L));

        List<CurrencyOperationsCreateEditDto> createEditDto2 = new ArrayList<>();
        createEditDto2.add(new CurrencyOperationsCreateEditDto("name2", 1L));
        currencyOperationsService.create(createEditDto2);
    }

    @Test
    public void test_create() {
        var result = currencyOperationsService.create(createEditDto);
        assertEquals(NAME, result.get(0).name());
    }

    @Test
    public void test_findAll() {
        var result = currencyOperationsService.findAll();
        assertFalse(result.isEmpty());
    }
}
