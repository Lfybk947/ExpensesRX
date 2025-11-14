package ru.lfybkCompany.service.fileService.expensesFileService;

import java.util.List;

public interface FilterExpensesFile extends FilterFile<List<String>, List<List<String>> > {
    List<List<String>> checkUnique(List<String> resource);
}
