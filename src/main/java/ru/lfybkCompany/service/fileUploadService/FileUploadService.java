package ru.lfybkCompany.service.fileUploadService;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.lfybkCompany.exception.FileUploadException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class FileUploadService {

    @Value("${app.upload.upload-file}")
    private String uploadDir;

    public void upload(String filePath, InputStream content) {
        try {

            Path fullFilePath = Path.of(uploadDir, filePath);

            try (content) {
                Files.createDirectories(fullFilePath.getParent());
                Files.write(fullFilePath, content.readAllBytes(),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
            }

        } catch (FileUploadException | IOException e) {
            throw new FileUploadException(("Failed upload file :%S").formatted(e.getMessage()));
        }
    }

    public List<String> get(String filePath) throws FileUploadException, IOException {
        try {

            Path fullFilePath = Path.of(uploadDir, filePath);

            return Files.exists(fullFilePath) ? Files.readAllLines(fullFilePath)
                    : List.of();

        } catch (FileUploadException | IOException e) {
            throw new FileUploadException(("Failed get file :%S").formatted(e.getMessage()));
        }
    }
}
