package ru.lfybkCompany.integration.service.entityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.dto.createReadDto.*;
import ru.lfybkCompany.dto.filterDto.ExpensesFilter;
import ru.lfybkCompany.dto.filterDto.ExpensesSessionFilter;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.service.entityService.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@IT
@AutoConfigureMockMvc
public class ExpensesServiceTest {

    @Autowired
    private ExpensesService expensesService;
    @Autowired
    private CurrencyOperationsService currencyOperationsService;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private DescriptionsService descriptionsService;
    @Autowired
    private UserService userService;

    private List<ExpensesCreateEditDto> createEditDto;
    private static final String NAME = "name";
    private static final Long USER_ID = 1L;

    @BeforeEach
    public void create() {
        UserCreateEditDto user = new UserCreateEditDto( "1", "1", "czare2015@yandex.ru",
                LocalDate.of(1999, 12, 22), "{noop}123", Role.ADMIN, Gender.MALE);
        userService.create(user);

        createEditDto = new ArrayList<>();
        createEditDto.add(new ExpensesCreateEditDto(
                LocalDateTime.of(1999, 10, 10, 23, 6),
                BigDecimal.valueOf(100L),
                1,
                1L,
                1L,
                1L
                ));
        currencyOperationsService.create(List.of(new CurrencyOperationsCreateEditDto(NAME)));
        categoriesService.create(List.of(new CategoriesCreateEditDto(NAME)));
        descriptionsService.create(List.of(new DescriptionsCreateEditDto(NAME)));

        List<ExpensesCreateEditDto> createEditDto2 = new ArrayList<>();
        createEditDto2.add(new ExpensesCreateEditDto(
                LocalDateTime.of(2000, 10, 10, 23, 6),
                BigDecimal.valueOf(200L),
                1,
                1L,
                1L,
                1L
        ));
        expensesService.create(createEditDto2);
    }

    @Test
    @WithMockUser(username = "czare2015@yandex.ru", password = "123", authorities = {"ADMIN", "USER"})
    public void test_create() {
        var result = expensesService.create(createEditDto);
        assertEquals(createEditDto.get(0).date(), result.get(0).date());
        assertEquals(createEditDto.get(0).sum(), result.get(0).sum());
        assertEquals(createEditDto.get(0).currencyOperationsID(), result.get(0).currencyOperations().id());
        assertEquals(createEditDto.get(0).categoriesID(), result.get(0).categories().id());
        assertEquals(createEditDto.get(0).descriptionsID(), result.get(0).descriptions().id());
        assertEquals(createEditDto.get(0).userID(), result.get(0).user().id());
        assertThat(result.get(0).id()).isNotZero();
    }

    @Test
    public void test_findAll() {
        var result = expensesService.findAll();
        assertFalse(result.isEmpty());
    }

    @Test
    @WithMockUser(username = "czare2015@yandex.ru", password = "123", authorities = {"ADMIN", "USER"})
    public void test_findAllExpensesByUserId() {
        var result = expensesService.findAllExpensesByUserId(USER_ID);
        assertFalse(result.isEmpty());
    }

    @Test
    @WithMockUser(username = "czare2015@yandex.ru", password = "123", authorities = {"ADMIN", "USER"})
    public void test_findAllByFilter() {
        ExpensesSessionFilter expensesFilter = new ExpensesSessionFilter(
                LocalDateTime.of(1998, 10, 10, 23, 6),
                LocalDateTime.of(2000, 10, 10, 23, 6),
                BigDecimal.valueOf(10L),
                BigDecimal.valueOf(1000L),
                1,
                List.of(1L),
                List.of(1L),
                List.of(1L));
        var result = expensesService.findAllByFilter(expensesFilter);
        assertFalse(result.isEmpty());
    }
}
