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
import ru.lfybkCompany.dto.fileDto.FileInfoDto;
import ru.lfybkCompany.exception.FileUploadException;
import ru.lfybkCompany.exception.InvalidFileTypeException;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.service.entityService.UserService;
import ru.lfybkCompany.service.fileService.FileServiceImpl;
import ru.lfybkCompany.service.fileService.expensesFileService.UploadExpensesServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static java.lang.System.currentTimeMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@IT
@AutoConfigureMockMvc
public class UploadExpensesServiceImplTest {

    @Value("${app.upload.upload-file}")
    private String uploadDir;
    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private UploadExpensesServiceImpl uploadExpensesService;

    private FileInfoDto fileInfoDto;
    private FileInfoDto fileInfoDto2;

    @BeforeEach
    public void create() throws IOException {
        UserCreateEditDto user = new UserCreateEditDto( "1", "1", "admin@admin.com",
                LocalDate.of(1999, 12, 22), "11", Role.ADMIN, Gender.MALE);
        userService.create(user);

        Path path = Paths.get("D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\operations.csv");
        MockMultipartFile file = new MockMultipartFile("file", "operations.csv",
                "text/csv",
                Files.readAllBytes(path));
        fileInfoDto = fileService.upload(file);

        Path path2 = Paths.get(
                "D:\\tests\\ExpensesRX\\src\\test\\resources\\testFiles\\Operations Wed Apr 21 2021-Sun Oct 26 2025.xls");
        MockMultipartFile file2 = new MockMultipartFile("file",
                "Operations Wed Apr 21 2021-Sun Oct 26 2025.xls",
                "text/xls",
                Files.readAllBytes(path2));
        fileInfoDto2 = fileService.upload(file2);
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "11", authorities = {"ADMIN", "USER"})
    public void test_uploadFileData_valid() {
        var n1 = currentTimeMillis();
        var name = uploadExpensesService.uploadFileData(fileInfoDto);
        assertEquals(name, fileInfoDto.getName());
        System.out.println(currentTimeMillis()-n1);
    }

    @Test
    @WithMockUser(username = "admin@admin.com", password = "11", authorities = {"ADMIN", "USER"})
    public void test_uploadFileData_not_valid() {
        boolean resultTest = true;
        try {
            uploadExpensesService.uploadFileData(fileInfoDto2);
        } catch (InvalidFileTypeException e) {
            resultTest = false;
        } catch (FileUploadException ex) {
            resultTest = false;
        } finally {
            assertFalse(resultTest);
        }
    }


}
