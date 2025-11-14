package ru.lfybkCompany.unitTest.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lfybkCompany.database.entity.Categories;
import ru.lfybkCompany.database.entity.CurrencyOperations;
import ru.lfybkCompany.database.entity.Descriptions;
import ru.lfybkCompany.dto.createReadDto.CategoriesCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.DescriptionsCreateEditDto;
import ru.lfybkCompany.mapper.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FieldExpensesObjectsMapperTest {
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

    private final CurrencyOperations currencyOperations = new CurrencyOperations(1, "1");
    private final CurrencyOperationsCreateEditDto currencyOperationsCreateEditDto =
            new CurrencyOperationsCreateEditDto( "11");

    private final Categories categories = new Categories(2L, "2");
    private final CategoriesCreateEditDto categoriesCreateEditDto = new CategoriesCreateEditDto( "22");

    private final Descriptions descriptions = new Descriptions(3L, "3");
    private final DescriptionsCreateEditDto descriptionsCreateEditDto = new DescriptionsCreateEditDto( "33");

    @Test
    public void test_currencyOperationsReadMapper() {
        var dto = currencyOperationsReadMapper.map(currencyOperations);
        assertEquals(dto.id(), currencyOperations.getId());
        assertEquals(dto.name(), currencyOperations.getName());
    }

    @Test
    public void test_currencyOperationsCreateEditMapper() {
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
        var dto = descriptionsCreateEditMapper.map(descriptionsCreateEditDto);
        assertThat(dto.getId() == null);
        assertEquals(dto.getName(), descriptionsCreateEditDto.name());
    }
}
