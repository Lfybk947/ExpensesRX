package ru.lfybkCompany.integration.service.fileService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.service.entityService.UserService;
import ru.lfybkCompany.service.fileService.FileServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
@AutoConfigureMockMvc
public class FileServiceImplTest {

    @Value("${app.upload.upload-file}")
    private String uploadDir;
    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private UserService userService;
    private String key;

    @BeforeEach
    public void create() throws IOException {
        UserCreateEditDto user = new UserCreateEditDto( "1", "1", "admin@admin.com",
                LocalDate.of(1999, 12, 22), "11", Role.ADMIN, Gender.MALE);
        userService.create(user);

        Path path = Paths.get("D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\operations.csv");
        MockMultipartFile file = new MockMultipartFile("file", "operations.csv",
                "text/csv",
                Files.readAllBytes(path));
        var fileInfoDto = fileService.upload(file);
        key = fileInfoDto.getKey();
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "11", authorities = {"ADMIN", "USER"})
    public void test_upload_1() throws IOException {
        Path path = Paths.get(
                "D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\Operations Wed Apr 21 2021-Sun Oct 26 2025.csv");
        MockMultipartFile file = new MockMultipartFile("file",
                "Operations Wed Apr 21 2021-Sun Oct 26 2025.csv", "text/csv",
                Files.readAllBytes(path));
        var fileName = fileService.upload(file);

        assertEquals("Operations Wed Apr 21 2021-Sun Oct 26 2025.csv", fileName.getName());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "11", authorities = {"ADMIN", "USER"})
    public void test_upload_2() throws IOException {
        Path path = Paths.get(
                "D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\Operations Wed Apr 21 2021-Sun Oct 26 2025.ofx");
        MockMultipartFile file = new MockMultipartFile("file",
                "Operations Wed Apr 21 2021-Sun Oct 26 2025.ofx", "text/ofx",
                Files.readAllBytes(path));
        var fileName = fileService.upload(file);

        assertEquals("Operations Wed Apr 21 2021-Sun Oct 26 2025.ofx", fileName.getName());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "11", authorities = {"ADMIN", "USER"})
    public void test_upload_3() throws IOException {
        Path path = Paths.get(
                "D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\Operations Wed Apr 21 2021-Sun Oct 26 2025.xls");
        MockMultipartFile file = new MockMultipartFile("file",
                "Operations Wed Apr 21 2021-Sun Oct 26 2025.xls", "text/xls",
                Files.readAllBytes(path));
        var fileName = fileService.upload(file);
        assertEquals("Operations Wed Apr 21 2021-Sun Oct 26 2025.xls", fileName.getName());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "11", authorities = {"ADMIN", "USER"})
    public void test_download() throws IOException {

        var resource = fileService.download(key);
        Path path = Path.of(uploadDir, key);

        assertEquals(resource.getFile().toPath(), path);
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "11", authorities = {"ADMIN", "USER"})
    public void test_get() {

        var list = fileService.get(key);

        assertThat(list.size()).isBetween(400, 6000);
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "11", authorities = {"ADMIN", "USER"})
    public void test_findById() {
        Long id = 1L;

        var fileInfoDto = fileService.findById(id);

        assertEquals(id, fileInfoDto.getId());
    }

}
