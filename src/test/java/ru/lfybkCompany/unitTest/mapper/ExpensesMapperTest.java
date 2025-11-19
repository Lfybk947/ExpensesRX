package ru.lfybkCompany.unitTest.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lfybkCompany.database.entity.*;
import ru.lfybkCompany.database.repository.CategoriesRepository;
import ru.lfybkCompany.database.repository.CurrencyOperationsRepository;
import ru.lfybkCompany.database.repository.DescriptionsRepository;
import ru.lfybkCompany.database.repository.UserRepository;
import ru.lfybkCompany.dto.createReadDto.*;
import ru.lfybkCompany.dto.filterDto.ExpensesSessionFilter;
import ru.lfybkCompany.mapper.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpensesMapperTest {
    @Mock
    private CurrencyOperationsReadMapper currencyOperationsReadMapper;
    @Mock
    private CategoriesReadMapper categoriesReadMapper;
    @Mock
    private DescriptionsReadMapper descriptionsReadMapper;
    @Mock
    private UserReadMapper userReadMapper;
    @Mock
    private CurrencyOperationsRepository currencyOperationsRepository;
    @Mock
    private CategoriesRepository categoriesRepository;
    @Mock
    private DescriptionsRepository descriptionsRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FilterExpensesMapper filterExpensesMapper;
    @InjectMocks
    private ExpensesReadMapper expensesReadMapper;
    @InjectMocks
    private ExpensesCreateEditMapper expensesCreateEditMapper;

    private final User user = new User(4L, "4", "2", "admin@admin.com",
            LocalDate.parse("1999-12-12"), "123", Role.ADMIN, Gender.FEMALE);
    private final ExpensesCreateEditDto expensesCreateEditDto = new ExpensesCreateEditDto(
            LocalDateTime.of(2025, 10, 12, 1, 1, 1, 1),
            BigDecimal.valueOf(123L),
            1,
            2L,
            3L,
            user.getId());
    private final Expenses expenses = new Expenses(
            1L,
            LocalDateTime.of(2025, 10, 12, 1, 1, 1, 1),
            BigDecimal.valueOf(123L),
            new CurrencyOperations(1, "1", user),
            new Categories(2L, "2", user),
            new Descriptions(3L, "3", user),
            user);

    private final CurrencyOperationsReadDto currencyOperationsReadDto = new CurrencyOperationsReadDto(1, "1", user);
    private final CategoriesReadDto categoriesReadDto = new CategoriesReadDto(2L, "2", user);
    private final DescriptionsReadDto descriptionsReadDto = new DescriptionsReadDto(3L, "3", user);

    private final CurrencyOperations currencyOperations = new CurrencyOperations(1, "1", user);
    private final Categories categories = new Categories(2L, "2", user);
    private final Descriptions descriptions = new Descriptions(3L, "3", user);

    private final UserReadDto userReadDto = new UserReadDto(4L, "4", "2", "admin@admin.com",
            LocalDate.parse("1999-12-12"), Role.ADMIN, Gender.FEMALE);

    private final ExpensesSessionFilter expensesSessionFilter = new ExpensesSessionFilter(
            LocalDateTime.of(1999, 10, 10, 20, 20, 20),
            LocalDateTime.of(2025, 12, 10, 20, 20, 20),
            BigDecimal.valueOf(0L),
            BigDecimal.valueOf(1200L),
            1, List.of(1L), List.of(1L), List.of(1L));

    @Test
    public void test_expensesReadMapper() {
        when(currencyOperationsReadMapper.map(expenses.getCurrencyOperations())).thenReturn(currencyOperationsReadDto);
        when(categoriesReadMapper.map(expenses.getCategories())).thenReturn(categoriesReadDto);
        when(descriptionsReadMapper.map(expenses.getDescriptions())).thenReturn(descriptionsReadDto);
        when(userReadMapper.map(expenses.getUser())).thenReturn(userReadDto);

        var dto = expensesReadMapper.map(expenses);
        assertEquals(dto.id(), expenses.getId());
        assertEquals(dto.date(), expenses.getDate());
        assertEquals(dto.sum(), expenses.getSum());
        assertEquals(dto.currencyOperations().id(), expenses.getCurrencyOperations().getId());
        assertEquals(dto.categories().id(), expenses.getCategories().getId());
        assertEquals(dto.descriptions().id(), expenses.getDescriptions().getId());
        assertEquals(dto.user().id(), expenses.getUser().getId());
    }

    @Test
    public void test_filterExpensesMapper() {
        var filter = filterExpensesMapper.map(expensesSessionFilter);
        assertEquals(filter.fromDate(), expensesSessionFilter.getFromDate());
        assertEquals(filter.toDate(), expensesSessionFilter.getToDate());
        assertEquals(filter.fromSum(), expensesSessionFilter.getFromSum());
        assertEquals(filter.toSum(), expensesSessionFilter.getToSum());
        assertEquals(filter.currencyOperations(), expensesSessionFilter.getCurrencyOperations());
        assertEquals(filter.categories(), expensesSessionFilter.getCategories());
        assertEquals(filter.descriptions(), expensesSessionFilter.getDescriptions());
        assertEquals(filter.users(), expensesSessionFilter.getUsers());
    }

    @Test
    public void test_expensesCreateEditMapper() {
        when(currencyOperationsRepository.findById(expensesCreateEditDto.currencyOperationsID()))
                .thenReturn(Optional.of(currencyOperations));
        when(categoriesRepository.findById(expensesCreateEditDto.categoriesID())).thenReturn(Optional.of(categories));
        when(descriptionsRepository.findById(expensesCreateEditDto.descriptionsID())).thenReturn(Optional.of(descriptions));
        when(userRepository.findById(expensesCreateEditDto.userID())).thenReturn(Optional.of(user));

        var expenses1 = expensesCreateEditMapper.map(expensesCreateEditDto);

        assertEquals(expenses1.getDate(), expensesCreateEditDto.date());
        assertEquals(expenses1.getSum(), expensesCreateEditDto.sum());
        assertEquals(expenses1.getCurrencyOperations().getId(), expensesCreateEditDto.currencyOperationsID());
        assertEquals(expenses1.getCategories().getId(), expensesCreateEditDto.categoriesID());
        assertEquals(expenses1.getDescriptions().getId(), expensesCreateEditDto.descriptionsID());
        assertEquals(expenses1.getUser().getId(), expensesCreateEditDto.userID());

    }
}
