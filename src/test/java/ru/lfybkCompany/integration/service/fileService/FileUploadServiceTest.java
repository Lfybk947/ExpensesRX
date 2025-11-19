package ru.lfybkCompany.integration.service.fileService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.service.entityService.UserService;
import ru.lfybkCompany.service.fileService.FileUploadService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@AutoConfigureMockMvc
public class FileUploadServiceTest {

    @Value("${app.upload.upload-file}")
    private String uploadDir;

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private UserService userService;

    private static final String TEST_FILE_NAME = "test";

    @BeforeEach
    public void create() throws IOException {
        UserCreateEditDto user = new UserCreateEditDto( "1", "1", "admin@admin.com",
                LocalDate.of(1999, 12, 22), "11", Role.ADMIN, Gender.MALE);
        userService.create(user);
        Path path = Paths.get("D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\Operations Wed Apr 21 2021-Sun Oct 26 2025.csv");
        MockMultipartFile file = new MockMultipartFile("file", "Operations Wed Apr 21 2021-Sun Oct 26 2025.csv",
                "text/csv",
                Files.readAllBytes(path));
        fileUploadService.upload(TEST_FILE_NAME, file.getInputStream());
    }

    @Test
    public void test_upload() throws IOException {
        Path path = Paths.get("D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\operations.csv");
        MockMultipartFile file = new MockMultipartFile("file", "operations.csv",
                "text/csv",
                Files.readAllBytes(path));
        fileUploadService.upload(TEST_FILE_NAME, file.getInputStream());
        Path path2 = Path.of(uploadDir, TEST_FILE_NAME);

        var file2 = Files.readAllLines(path2);
        assertFalse(file2.isEmpty());
        assertThat(file2.size()).isBetween(600, 6000);

    }

    @Test
    public void test_get() {
        var file = fileUploadService.get(TEST_FILE_NAME);

        assertFalse(file.isEmpty());
        assertThat(file.size()).isBetween(600, 6000);
    }

    @Test
    public void test_download() throws IOException {
        var file = fileUploadService.download(TEST_FILE_NAME);

        assertTrue(file.exists());
        assertThat(file.contentLength()).isGreaterThan(6000L);
    }
}
