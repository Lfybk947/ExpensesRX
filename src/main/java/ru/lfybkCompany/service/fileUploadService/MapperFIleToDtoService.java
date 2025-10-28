package ru.lfybkCompany.service.fileUploadService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lfybkCompany.dto.createReadDto.*;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.exception.FileUploadException;
import ru.lfybkCompany.service.entityService.CategoriesService;
import ru.lfybkCompany.service.entityService.CurrencyOperationsService;
import ru.lfybkCompany.service.entityService.UserService;
import ru.lfybkCompany.service.entityService.DescriptionsService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapperFIleToDtoService {
    private final CheckUniqueFileStringToDB checkUniqueFileStringToDB;
    private final UserService userService;
    private final CurrencyOperationsService currencyOperationsService;
    private final CategoriesService categoriesService;
    private final DescriptionsService descriptionsService;

    private List<CurrencyOperationsReadDto> coDto;
    private List<CategoriesReadDto> cDto;
    private List<DescriptionsReadDto> dDto;

    private List<List<String>> expensesFile;

    public void getExpensesFile(String filePath) throws FileProcessingException {
        if (expensesFile == null) {
            expensesFile = checkUniqueFileStringToDB.checkUnique(filePath);
        }
    }

    public List<CurrencyOperationsCreateEditDto> createCOced() {
        try {

        coDto = currencyOperationsService.findAll();

        return expensesFile.stream()
                .skip(1)
                .map((arr) -> arr.get(2))
                .distinct()
                .filter((co)-> coDto.stream().noneMatch((c)-> co.contains(c.name())))
                .map(CurrencyOperationsCreateEditDto::new)
                .toList();

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Failed mapping to CurrencyOperationsCreateEditDto :%S").formatted(e.getMessage()));
        }
    }

    public List<CategoriesCreateEditDto> createCced() {
        try {

        cDto = categoriesService.findAll();

        return expensesFile.stream()
                .skip(1)
                .map((list) -> list.get(3))
                .distinct()
                .filter((ca)-> cDto.stream().noneMatch((c)-> ca.contains(c.name())))
                .map(CategoriesCreateEditDto::new
                )
                .toList();

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Failed mapping to CategoriesCreateEditDto :%S").formatted(e.getMessage()));
        }
    }

    public List<DescriptionsCreateEditDto> createDced() {
        try {

        dDto = descriptionsService.findAll();

        return expensesFile.stream()
                .skip(1)
                .map((list) -> list.get(4))
                .distinct()
                .filter((de)-> dDto.stream().noneMatch((d)-> de.equals(d.name())))
                .map(DescriptionsCreateEditDto::new)
                .toList();

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Failed mapping to DescriptionsCreateEditDto :%S").formatted(e.getMessage()));
        }
    }

    public List<ExpensesCreateEditDto> createEced() {
        try {

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

            Long userId = userService.getAuthorizationUser().get().id();
            coDto = currencyOperationsService.findAll();
            cDto = categoriesService.findAll();
            dDto = descriptionsService.findAll();

            return expensesFile.stream()
                    .skip(1)
                    .map((arr2) ->
                            new ExpensesCreateEditDto(LocalDateTime.parse(arr2.get(0), dateTimeFormatter),
                                    BigDecimal.valueOf(Double.parseDouble(arr2.get(1))),
                                    getIdCurrencyOperations(arr2.get(2)),
                                    getIdCategories(arr2.get(3)),
                                    getIdDescriptions(arr2.get(4)),
                                    userId)
                    )
                    .toList();

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Failed mapping to ExpensesCreateEditDto :%S").formatted(e.getMessage()));
        }
    }

    private Integer getIdCurrencyOperations(String string) {
        return coDto.stream()
                .filter((co)-> co.name().equals(string))
                .map(CurrencyOperationsReadDto::id).findFirst().get();
    }

    private Long getIdCategories(String string) {
        return cDto.stream()
                .filter((c)-> c.name().equals(string))
                .map(CategoriesReadDto::id).findFirst().get();
    }

    private Long getIdDescriptions(String string) {
        return dDto.stream()
                .filter((d)-> d.name().equals(string))
                .map(DescriptionsReadDto::id).findFirst().get();
    }


}
