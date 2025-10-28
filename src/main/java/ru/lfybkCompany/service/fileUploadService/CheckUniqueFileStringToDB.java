package ru.lfybkCompany.service.fileUploadService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.lfybkCompany.database.entity.Expenses;
import ru.lfybkCompany.database.repository.ExpensesRepository;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.exception.FileUploadException;
import ru.lfybkCompany.service.entityService.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CheckUniqueFileStringToDB {
    private final FileUploadService file;
    private final UserService userService;
    private final ExpensesRepository expensesRepository;

    private List<String[]> files;
    private List<Integer> integers;


    public List<List<String>> checkUnique(String filePath) {
        try {

            List<String> columnsName = List.of("Дата операции", "Сумма операции",
                    "Валюта операции", "Категория", "Описание");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

            var eDto = expensesRepository.findAllExpensesByUserId(userService.getAuthorizationUser().get().id());


            List<List<String>> list = new java.util.ArrayList<>(getFile(filePath).stream()
                    .skip(1)
                    .map((arr) -> mapArrToList(filePath, columnsName, arr))
                    .filter((ex) -> !eDto.stream().map(Expenses::getDate).toList()
                            .contains(LocalDateTime.parse(ex.get(0), dateTimeFormatter)))
                    .toList());
            list.add(0, mapArrToList(filePath, columnsName, getFile(filePath).get(0)));
            return list;

        } catch (FileProcessingException | IOException e) {
            throw new FileProcessingException(("Failed convert file: %S").formatted(e.getMessage()));
        }
    }

    private List<String[]> getFile(String filePath) throws IOException {
        try {

            return files == null ? files = file.get(filePath).stream()
                    .map(this::convertString).toList() : files;

        } catch (FileUploadException | IOException e) {
            throw new FileUploadException(("Failed to get file: %S").formatted(e.getMessage()));
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

    private List<String> mapArrToList(String filePath, List<String> columnsName, String[] arr) {
        try {

            return getIColumns(filePath, columnsName).stream()
                    .map(idx -> arr[idx])
                    .toList();

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Incorrect columns in first string files: %S").formatted(e.getMessage()));
        }
    }

    private List<Integer> getIColumns(String filePath, List<String> columnsName) {
        try {

            String[] string = getFile(filePath).get(0);

            return integers == null ? integers = IntStream.range(0, string.length)
                    .filter((in) -> columnsName.contains(string[in]))
                    .boxed()
                    .toList() : integers;

        } catch (FileProcessingException | IOException e) {
            throw new FileProcessingException(("Incorrect first line in file, column names could not be recognized: %S").formatted(e.getMessage()));
        }
    }

}
