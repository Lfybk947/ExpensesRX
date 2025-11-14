package ru.lfybkCompany.service.fileService;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.lfybkCompany.dto.fileDto.FileInfoDto;

import java.util.List;

public interface FileService {
    FileInfoDto upload(MultipartFile resource);

    Resource download(String key);

    FileInfoDto findById(Long id);

    List<String> get(String key);
}
