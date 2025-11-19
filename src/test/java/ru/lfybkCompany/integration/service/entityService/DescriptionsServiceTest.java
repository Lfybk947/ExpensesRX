package ru.lfybkCompany.integration.service.entityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.dto.createReadDto.DescriptionsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.service.entityService.DescriptionsService;
import ru.lfybkCompany.service.entityService.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@IT
@AutoConfigureMockMvc
public class DescriptionsServiceTest {

    @Autowired
    private DescriptionsService descriptionsService;
    @Autowired
    private UserService userService;

    private List<DescriptionsCreateEditDto> createEditDto;

    @BeforeEach
    public void create() {
        UserCreateEditDto user = new UserCreateEditDto( "1", "1", "admin@admin.com",
                LocalDate.of(1999, 12, 22), "11", Role.ADMIN, Gender.MALE);
        userService.create(user);

        createEditDto = new ArrayList<>();
        createEditDto.add(new DescriptionsCreateEditDto("1", 1L));

        List<DescriptionsCreateEditDto> createEditDto2 = new ArrayList<>();
        createEditDto2.add(new DescriptionsCreateEditDto("name2", 1L));
        descriptionsService.create(createEditDto2);
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "11", authorities = {"ADMIN", "USER"})
    public void test_create() {
        var result = descriptionsService.create(createEditDto);
        assertEquals("1", result.get(0).name());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "11", authorities = {"ADMIN", "USER"})
    public void test_findAll() {
        var result = descriptionsService.findAll();
        assertFalse(result.isEmpty());
    }
}
