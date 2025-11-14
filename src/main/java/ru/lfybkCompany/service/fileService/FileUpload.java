package ru.lfybkCompany.service.fileService;

import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;

public interface FileUpload extends Upload<String, InputStream, List<String>, Resource>{
    void upload(String filePath, InputStream content);

    List<String> get(String filePath);

    Resource download(String key);
}
