package ru.lfybkCompany.integration.service.entityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import ru.lfybkCompany.dto.createReadDto.CategoriesCreateEditDto;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.service.entityService.CategoriesService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@IT
@AutoConfigureMockMvc
public class CategoriesServiceTest {

    @Autowired
    private CategoriesService categoriesService;

    private List<CategoriesCreateEditDto> createEditDto;
    private static final String NAME = "name";

    @BeforeEach
    public void create() {
        createEditDto = new ArrayList<>();
        createEditDto.add(new CategoriesCreateEditDto(NAME));

        List<CategoriesCreateEditDto> createEditDto2 = new ArrayList<>();
        createEditDto2.add(new CategoriesCreateEditDto("name2"));
        categoriesService.create(createEditDto2);
    }

    @Test
    public void test_create() {
        var result = categoriesService.create(createEditDto);
        assertEquals(NAME, result.get(0).name());
    }

    @Test
    public void test_findAll() {
        var result = categoriesService.findAll();
        assertFalse(result.isEmpty());
    }
}
