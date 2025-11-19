package ru.lfybkCompany.unitTest.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lfybkCompany.database.entity.*;
import ru.lfybkCompany.database.repository.UserRepository;
import ru.lfybkCompany.dto.createReadDto.CategoriesCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.DescriptionsCreateEditDto;
import ru.lfybkCompany.mapper.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FieldExpensesObjectsMapperTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CurrencyOperationsReadMapper currencyOperationsReadMapper;
    @InjectMocks
    private CategoriesReadMapper categoriesReadMapper;
    @InjectMocks
    private DescriptionsReadMapper descriptionsReadMapper;
    @InjectMocks
    private CurrencyOperationsCreateEditMapper currencyOperationsCreateEditMapper;
    @InjectMocks
    private CategoriesCreateEditMapper categoriesCreateEditMapper;
    @InjectMocks
    private DescriptionsCreateEditMapper descriptionsCreateEditMapper;

    private final User user = new User(1L, "1", "2", "admin@admin.com",
            LocalDate.parse("1999-12-12"), "123", Role.ADMIN, Gender.FEMALE);
    private final CurrencyOperations currencyOperations = new CurrencyOperations(1, "1", user);
    private final CurrencyOperationsCreateEditDto currencyOperationsCreateEditDto =
            new CurrencyOperationsCreateEditDto( "11", user.getId());

    private final Categories categories = new Categories(2L, "2", user);
    private final CategoriesCreateEditDto categoriesCreateEditDto = new CategoriesCreateEditDto( "22", user.getId());

    private final Descriptions descriptions = new Descriptions(3L, "3", user);
    private final DescriptionsCreateEditDto descriptionsCreateEditDto = new DescriptionsCreateEditDto( "33", user.getId());

    @Test
    public void test_currencyOperationsReadMapper() {
        var dto = currencyOperationsReadMapper.map(currencyOperations);
        assertEquals(dto.id(), currencyOperations.getId());
        assertEquals(dto.name(), currencyOperations.getName());
    }

    @Test
    public void test_currencyOperationsCreateEditMapper() {
        when(userRepository.findById(currencyOperationsCreateEditDto.userID())).thenReturn(Optional.of(user));

        var dto = currencyOperationsCreateEditMapper.map(currencyOperationsCreateEditDto);
        assertThat(dto.getId() == null);
        assertEquals(dto.getName(), currencyOperationsCreateEditDto.name());
    }

    @Test
    public void test_categoriesReadMapper() {
        var dto = categoriesReadMapper.map(categories);
        assertEquals(dto.id(), categories.getId());
        assertEquals(dto.name(), categories.getName());
    }

    @Test
    public void test_categoriesCreateEditMapper() {
        when(userRepository.findById(categoriesCreateEditDto.userID())).thenReturn(Optional.of(user));

        var dto = categoriesCreateEditMapper.map(categoriesCreateEditDto);
        assertThat(dto.getId() == null);
        assertEquals(dto.getName(), categoriesCreateEditDto.name());
    }

    @Test
    public void test_descriptionsReadMapper() {
        var dto = descriptionsReadMapper.map(descriptions);
        assertEquals(dto.id(), descriptions.getId());
        assertEquals(dto.name(), descriptions.getName());
    }

    @Test
    public void test_descriptionsCreateEditMapper() {
        when(userRepository.findById(descriptionsCreateEditDto.userID())).thenReturn(Optional.of(user));

        var dto = descriptionsCreateEditMapper.map(descriptionsCreateEditDto);
        assertThat(dto.getId() == null);
        assertEquals(dto.getName(), descriptionsCreateEditDto.name());
    }
}
