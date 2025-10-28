package ru.lfybkCompany.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.integration.annotation.IT;
import ru.lfybkCompany.service.fileUploadService.FileUploadToDBService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@IT
@AutoConfigureMockMvc
public class FileUploadToDBServiceTest {

    @Autowired
    private FileUploadToDBService fileUploadToDBService;

    @Test
    @WithMockUser(username = "czare2015@yandex.ru", password = "123", authorities = {"ADMIN", "USER"})
    public void test_uploadExpensesFileData_validData() throws IOException {
        Path path = Paths.get("D:/tests/ExpensesRX/Files/operations.csv");
        MockMultipartFile file = new MockMultipartFile("file", "operations.csv", "text/csv",
                Files.readAllBytes(path));
        var fileName = fileUploadToDBService.uploadExpensesFileData(file);

        assertEquals("operations.csv", fileName);
    }

    @Test
    @WithMockUser(username = "czare2015@yandex.ru", password = "123", authorities = {"ADMIN", "USER"})
    public void test_uploadExpensesFileData_notValidData_ofx() throws IOException {
        Path path = Paths.get("D:/tests/ExpensesRX/Operations Wed Apr 21 2021-Sun Oct 26 2025.ofx");
        MockMultipartFile file = new MockMultipartFile("file",
                "Operations Wed Apr 21 2021-Sun Oct 26 2025.ofx", "text/ofx",
                Files.readAllBytes(path));
        var fileName = fileUploadToDBService.uploadExpensesFileData(file);

        assertEquals("Operations Wed Apr 21 2021-Sun Oct 26 2025.ofx", fileName);
    }

    @Test
    @WithMockUser(username = "czare2015@yandex.ru", password = "123", authorities = {"ADMIN", "USER"})
    public void test_uploadExpensesFileData_notValidData_xls() throws IOException {
        Path path = Paths.get("D:/tests/ExpensesRX/Operations Wed Apr 21 2021-Sun Oct 26 2025.xls");
        boolean resultTest = true;
        try {
            MockMultipartFile file = new MockMultipartFile("file",
                    "Operations Wed Apr 21 2021-Sun Oct 26 2025.xls", "text/xls",
                    Files.readAllBytes(path));
            fileUploadToDBService.uploadExpensesFileData(file);
        } catch (FileProcessingException e) {
            resultTest = false;
            assertFalse(resultTest);
        }
        assertFalse(resultTest);
    }

}
