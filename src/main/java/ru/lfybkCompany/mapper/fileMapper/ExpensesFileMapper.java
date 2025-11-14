package ru.lfybkCompany.mapper.fileMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.dto.createReadDto.CategoriesReadDto;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsReadDto;
import ru.lfybkCompany.dto.createReadDto.DescriptionsReadDto;
import ru.lfybkCompany.dto.createReadDto.ExpensesCreateEditDto;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.mapper.Mapper;
import ru.lfybkCompany.service.entityService.*;
import ru.lfybkCompany.service.fileService.expensesFileService.FilterExpensesFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpensesFileMapper implements Mapper<List<List<String>>, List<ExpensesCreateEditDto>> {
    private final UserService userService;
    private final CurrencyOperationsService currencyOperationsService;
    private final CategoriesService categoriesService;
    private final DescriptionsService descriptionsService;

    @Override
    public List<ExpensesCreateEditDto> map(List<List<String>> entity) {
        try {
            var user = userService.getAuthorizationUser().orElseThrow().id();
            var currencyOperations = currencyOperationsService.findAll();
            var categories = categoriesService.findAll();
            var descriptions = descriptionsService.findAll();

            return entity.stream()
                    .map((arr2) ->
                            new ExpensesCreateEditDto(LocalDateTime.parse(arr2.get(0), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                                    BigDecimal.valueOf(Double.parseDouble(arr2.get(1))),
                                    getIdCurrencyOperations(currencyOperations, arr2.get(2)),
                                    getIdCategories(categories, arr2.get(3)),
                                    getIdDescriptions(descriptions, arr2.get(4)),
                                    user))
                    .toList();// O(N)

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Failed mapping to ExpensesCreateEditDto :%S").formatted(e.getMessage()));
        }
    }

    private Integer getIdCurrencyOperations(List<CurrencyOperationsReadDto> list, String string) {
        return list.stream()
                .filter((co)-> co.name().equals(string))
                .map(CurrencyOperationsReadDto::id).findFirst().orElseThrow();
    }

    private Long getIdCategories(List<CategoriesReadDto> list, String string) {
        return list.stream()
                .filter((c)-> c.name().equals(string))
                .map(CategoriesReadDto::id).findFirst().orElseThrow();
    }

    private Long getIdDescriptions(List<DescriptionsReadDto> list, String string) {
        return list.stream()
                .filter((d)-> d.name().equals(string))
                .map(DescriptionsReadDto::id).findFirst().orElseThrow();
    }
}
