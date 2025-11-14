package ru.lfybkCompany.service.fileService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import ru.lfybkCompany.exception.FileDownloadException;
import ru.lfybkCompany.exception.FileUploadException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class FileUploadService implements FileUpload {

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

    public List<String> get(String key) {
        try {

            Path fullFilePath = Path.of(uploadDir, key);

            return Files.exists(fullFilePath) ? Files.readAllLines(fullFilePath)
                    : List.of();

        } catch (FileUploadException | IOException e) {
            throw new FileUploadException(("Failed get file :%S").formatted(e.getMessage()));
        }
    }

    public Resource download(String key) {
        try {
            Path filePath = Path.of(uploadDir, key);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileDownloadException("Failed download file");
            }
        } catch (IOException e) {
            throw new FileDownloadException("Failed download file");
        }
    }
}
