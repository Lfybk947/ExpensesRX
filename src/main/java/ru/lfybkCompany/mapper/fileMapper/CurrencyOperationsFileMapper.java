package ru.lfybkCompany.mapper.fileMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsReadDto;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.mapper.Mapper;
import ru.lfybkCompany.service.entityService.CurrencyOperationsService;
import ru.lfybkCompany.service.fileService.expensesFileService.FilterExpensesFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CurrencyOperationsFileMapper implements
        Mapper<List<List<String>>, List<CurrencyOperationsCreateEditDto>> {
    private final CurrencyOperationsService currencyOperationsService;

    @Override
    public List<CurrencyOperationsCreateEditDto> map(List<List<String>> entity) {
        try {
            List<CurrencyOperationsReadDto> coDto = currencyOperationsService.findAll();

            return entity.stream()
                    .map((arr) -> arr.get(2))
                    .distinct()
                    .filter((co)-> coDto.stream().noneMatch((c)-> co.contains(c.name())))
                    .map(CurrencyOperationsCreateEditDto::new)
                    .toList();// O(N)

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Failed mapping to CurrencyOperationsCreateEditDto :%S")
                    .formatted(e.getMessage()));
        }
    }
}
