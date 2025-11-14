package ru.lfybkCompany.integration.service.entityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import ru.lfybkCompany.dto.createReadDto.DescriptionsCreateEditDto;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.service.entityService.DescriptionsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@IT
@AutoConfigureMockMvc
public class DescriptionsServiceTest {

    @Autowired
    private DescriptionsService descriptionsService;

    private List<DescriptionsCreateEditDto> createEditDto;
    private static final String NAME = "name";

    @BeforeEach
    public void create() {
        createEditDto = new ArrayList<>();
        createEditDto.add(new DescriptionsCreateEditDto(NAME));

        List<DescriptionsCreateEditDto> createEditDto2 = new ArrayList<>();
        createEditDto2.add(new DescriptionsCreateEditDto("name2"));
        descriptionsService.create(createEditDto2);
    }

    @Test
    public void test_create() {
        var result = descriptionsService.create(createEditDto);
        assertEquals(NAME, result.get(0).name());
    }

    @Test
    public void test_findAll() {
        var result = descriptionsService.findAll();
        assertFalse(result.isEmpty());
    }
}
