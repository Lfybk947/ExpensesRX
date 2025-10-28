package ru.lfybkCompany.unitTest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lfybkCompany.database.entity.*;
import ru.lfybkCompany.database.repository.ExpensesRepository;
import ru.lfybkCompany.dto.createReadDto.*;
import ru.lfybkCompany.dto.filterDto.ExpensesFilter;
import ru.lfybkCompany.dto.filterDto.ExpensesSessionFilter;
import ru.lfybkCompany.mapper.ExpensesCreateEditMapper;
import ru.lfybkCompany.mapper.ExpensesReadMapper;
import ru.lfybkCompany.mapper.FilterExpensesMapper;
import ru.lfybkCompany.service.entityService.ExpensesService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpensesServiceTest {
    @Mock
    private ExpensesCreateEditMapper expensesCreateEditMapper;
    @Mock
    private ExpensesRepository expensesRepository;
    @Mock
    private ExpensesReadMapper expensesReadMapper;
    @Mock
    private FilterExpensesMapper filterExpensesMapper;

    @InjectMocks
    private ExpensesService expensesService;

    List<ExpensesCreateEditDto> dtoList = List.of(new ExpensesCreateEditDto(
            LocalDateTime.of(2025, 10, 12, 1, 1, 1, 1),
            BigDecimal.valueOf(123L),
            1,
            1L,
            1L,
            1L
    ));
    Expenses expenses = new Expenses(
            1L,
            LocalDateTime.of(2025, 10, 12, 1, 1, 1, 1),
            BigDecimal.valueOf(123L),
            new CurrencyOperations(1, "1"),
            new Categories(1L, "1"),
            new Descriptions(1L, "1"),
            new User(1L, "1", "2", "2@2.com",
                    LocalDate.parse("1999-12-12"), "123", Role.ADMIN, Gender.FEMALE)
    );
    ExpensesReadDto expensesReadDto = new ExpensesReadDto(
            1L,
            LocalDateTime.of(2025, 10, 12, 1, 1, 1, 1),
            BigDecimal.valueOf(123L),
            new CurrencyOperationsReadDto(1, "1"),
            new CategoriesReadDto(1L, "1"),
            new DescriptionsReadDto(1L, "1"),
            new UserReadDto(1L, "1", "2", "2@2.com",
                    LocalDate.parse("1999-12-12"), Role.ADMIN, Gender.FEMALE)
    );

    ExpensesSessionFilter filterDto = new ExpensesSessionFilter(
            LocalDateTime.of(1999, 10, 10, 20, 20, 20),
            LocalDateTime.of(2025, 12, 10, 20, 20, 20),
            BigDecimal.valueOf(0L),
            BigDecimal.valueOf(1200L),
            1, List.of(1L), List.of(1L), List.of(1L)
    );
    ExpensesFilter filter = new ExpensesFilter(
            LocalDateTime.of(1999, 10, 10, 20, 20, 20),
            LocalDateTime.of(2025, 12, 10, 20, 20, 20),
            BigDecimal.valueOf(0L),
            BigDecimal.valueOf(1200L),
            1, List.of(1L), List.of(1L), List.of(1L)
    );

    @Test
    public void test_create_validData() {

        when(expensesCreateEditMapper.map(dtoList.get(0))).thenReturn(expenses);
        when(expensesRepository.save(expenses)).thenReturn(expenses);
        when(expensesReadMapper.map(expenses)).thenReturn(expensesReadDto);

        var exp = expensesService.create(dtoList);

        assertEquals(exp.get(0).date(), dtoList.get(0).date());
    }

    @Test
    public void test_findAllByFilter_validData() {
        when(filterExpensesMapper.map(filterDto)).thenReturn(filter);
        when(expensesRepository.findAllFilter(filter)).thenReturn(List.of(expenses));
        when(expensesReadMapper.map(expenses)).thenReturn(expensesReadDto);

        var exp = expensesService.findAllByFilter(filterDto);

        assertTrue(exp.get(0).date().isAfter(filterDto.getFromDate()));
        assertTrue(exp.get(0).date().isBefore(filterDto.getToDate()));
    }
}
