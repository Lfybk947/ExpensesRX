package ru.lfybkCompany.service.fileService.expensesFileService;

public interface UploadService <F, T> {
    T uploadFileData(F file);
}
