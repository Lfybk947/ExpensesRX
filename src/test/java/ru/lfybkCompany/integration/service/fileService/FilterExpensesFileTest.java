package ru.lfybkCompany.integration.service.fileService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.service.entityService.UserService;
import ru.lfybkCompany.service.fileService.FileUploadService;
import ru.lfybkCompany.service.fileService.expensesFileService.FilterExpensesFileImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@AutoConfigureMockMvc
public class FilterExpensesFileTest {

    @Autowired
    private FilterExpensesFileImpl filterExpensesFile;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private UserService userService;

    private static final String TEST_FILE_NAME = "test";
    private static final String TEST_FILE_NAME_2 = "test2";

    @BeforeEach
    public void getFileList() throws IOException {
        UserCreateEditDto user = new UserCreateEditDto( "1", "1", "czare2015@yandex.ru",
                LocalDate.of(1999, 12, 22), "{noop}123", Role.ADMIN, Gender.MALE);
        userService.create(user);
        Path path = Paths.get("D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\operations.csv");
        MockMultipartFile file = new MockMultipartFile("file", "testFiles/operations.csv",
                "text/csv",
                Files.readAllBytes(path));
        fileUploadService.upload(TEST_FILE_NAME, file.getInputStream());

        Path path2 = Paths.get("D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\Operations Wed Apr 21 2021-Sun Oct 26 2025.ofx");
        MockMultipartFile file2 = new MockMultipartFile("file", "Operations Wed Apr 21 2021-Sun Oct 26 2025.ofx",
                "text/ofx",
                Files.readAllBytes(path2));
        fileUploadService.upload(TEST_FILE_NAME_2, file2.getInputStream());
    }

    @Test
    @WithMockUser(username = "czare2015@yandex.ru", password = "123", authorities = {"ADMIN", "USER"})
    public void test_checkUnique_valid() {
        var list2 = filterExpensesFile.checkUnique(fileUploadService.get(TEST_FILE_NAME));
        assertThat(list2.get(0).size()).isEqualTo(5);

    }

    @Test
    @WithMockUser(username = "czare2015@yandex.ru", password = "123", authorities = {"ADMIN", "USER"})
    public void test_checkUnique_not_valid() {
        var list2 = filterExpensesFile.checkUnique(fileUploadService.get(TEST_FILE_NAME_2));
        assertThat(list2.isEmpty());

    }
}
