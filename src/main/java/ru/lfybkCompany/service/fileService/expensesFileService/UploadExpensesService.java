package ru.lfybkCompany.service.fileService.expensesFileService;

import ru.lfybkCompany.dto.fileDto.FileInfoDto;

public interface UploadExpensesService extends UploadService<FileInfoDto, String> {
    String uploadFileData(FileInfoDto file);
}
