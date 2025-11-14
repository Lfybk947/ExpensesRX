package ru.lfybkCompany.service.fileService.expensesFileService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.Expenses;
import ru.lfybkCompany.database.repository.ExpensesRepository;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.service.entityService.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class FilterExpensesFileImpl implements FilterExpensesFile {
    private final UserService userService;
    private final ExpensesRepository expensesRepository;

    public List<List<String>> checkUnique(List<String> file) {
        try {
                List<String> columnsName = List.of("Дата операции", "Сумма операции",
                        "Валюта операции", "Категория", "Описание");
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

                var eDto = expensesRepository.findAllExpensesByUserId(
                        userService.getAuthorizationUser().orElseThrow().id());

                List<String[]> table = file.stream().map(this::convertString).toList();
                List<Integer> columns = getIColumns(table, columnsName);

            return table.stream()
                        .skip(1)
                        .map((arr) -> mapArrToList(columns, arr))
                        .filter((ex) -> !eDto.stream().map(Expenses::getDate)
                                .equals(LocalDateTime.parse(ex.get(0), dateTimeFormatter)))
                        .toList();// O(N)

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Failed convert file: %S").formatted(e.getMessage()));
        }
    }

    private String[] convertString(String string) {
        try {

            return string.replace("\"", "")
                    .replace(",", ".")
                    .split(";");

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Incorrect convert string to array: %S").formatted(e.getMessage()));
        }
    }

    private List<String> mapArrToList(List<Integer> columns, String[] arr) {
        try {

            return columns.stream()
                    .map(idx -> arr[idx])
                    .toList();

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Incorrect columns in first string files: %S")
                    .formatted(e.getMessage()));
        }
    }

    private List<Integer> getIColumns(List<String[]> file, List<String> columnsName) {
        try {

            String[] string = file.get(0);

            return IntStream.range(0, string.length)
                    .filter((in) -> columnsName.contains(string[in]))
                    .boxed()
                    .toList();

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Incorrect first line in file, column names could not be recognized: %S")
                    .formatted(e.getMessage()));
        }
    }

}
