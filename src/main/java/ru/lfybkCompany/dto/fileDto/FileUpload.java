package ru.lfybkCompany.dto.fileDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUpload {
    private MultipartFile multipartFile;
}
