package ru.lfybkCompany.service.fileService.expensesFileService;

public interface FilterFile<F, T> {
    T checkUnique(F filePath);
}
