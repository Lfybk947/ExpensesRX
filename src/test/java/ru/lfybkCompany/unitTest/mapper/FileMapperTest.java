package ru.lfybkCompany.unitTest.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.dto.createReadDto.*;
import ru.lfybkCompany.mapper.fileMapper.*;
import ru.lfybkCompany.service.entityService.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileMapperTest {
    @Mock
    private CategoriesService categoriesService;
    @Mock
    private DescriptionsService descriptionsService;
    @Mock
    private CurrencyOperationsService currencyOperationsService;
    @Mock
    private ExpensesService expensesService;
    @Mock
    private UserService userService;

    @InjectMocks
    private CategoriesFileMapper categoriesFileMapper;
    @InjectMocks
    private CurrencyOperationsFileMapper currencyOperationsFileMapper;
    @InjectMocks
    private DescriptionsFileMapper descriptionsFileMapper;
    @InjectMocks
    private ExpensesFileMapper expensesFileMapper;
    @InjectMocks
    private FileInfoCreateEditMapper fileInfoCreateEditMapper;
    @InjectMocks
    private FileInfoReadMapper fileInfoReadMapper;

    List<List<String>> entity;

    @BeforeEach
    public void getEntityList() throws IOException {
        var file = Files.readAllLines(Path.of(
                "D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\operations.csv"));

        List<String> columnsName = List.of("Дата операции", "Сумма операции",
                "Валюта операции", "Категория", "Описание");

        List<String[]> table = file.stream().map(this::convertString).toList();
        List<Integer> columns = getIColumns(table, columnsName);

        entity = table.stream()
                .skip(1)
                .map((arr) -> mapArrToList(columns, arr))
                .toList();// O(N)
    }

    private String[] convertString(String string) {
            return string.replace("\"", "")
                    .replace(",", ".")
                    .split(";");
    }

    private List<String> mapArrToList(List<Integer> columns, String[] arr) {
            return columns.stream()
                    .map(idx -> arr[idx])
                    .toList();
    }

    private List<Integer> getIColumns(List<String[]> file, List<String> columnsName) {
            String[] string = file.get(0);

            return IntStream.range(0, string.length)
                    .filter((in) -> columnsName.contains(string[in]))
                    .boxed()
                    .toList();
    }

    @Test
    public void test_categoriesFileMapper() {
        when(categoriesService.findAll()).thenReturn(List.of());
        var result = categoriesFileMapper.map(entity);

        assertEquals(result.get(0).name(), entity.get(0).get(3));
    }

    @Test
    public void test_descriptionsFileMapper() {
        when(descriptionsService.findAll()).thenReturn(List.of());
        var result = descriptionsFileMapper.map(entity);

        assertEquals(result.get(0).name(), entity.get(0).get(4));
    }

    @Test
    public void test_currencyOperationsFileMapper() {
        when(currencyOperationsService.findAll()).thenReturn(List.of());
        var result = currencyOperationsFileMapper.map(entity);

        assertEquals(result.get(0).name(), entity.get(0).get(2));
    }

    @Test
    public void test_expensesFileMapper() {
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        Optional<UserReadDto> userReadDto = Optional.of(new UserReadDto(1L, "Danil",
                "Tsaregorodtsev",
                "czare2015@yandex.ru",
                LocalDate.parse("2001-07-21"),
                Role.ADMIN,
                Gender.MALE));

        when(userService.getAuthorizationUser()).thenReturn(userReadDto);
        when(categoriesService.findAll()).thenReturn(entity.stream()
                .map((x)-> new CategoriesReadDto(1L, x.get(3))).toList());
        when(descriptionsService.findAll()).thenReturn(entity.stream()
                .map((x)-> new DescriptionsReadDto(1L, x.get(4))).toList());
        when(currencyOperationsService.findAll()).thenReturn(entity.stream()
                .map((x)-> new CurrencyOperationsReadDto(1, x.get(2))).toList());

        var result = expensesFileMapper.map(entity);

        assertEquals(result.get(0).date(), LocalDateTime.parse(entity.get(0).get(0), formatter));
    }
}
