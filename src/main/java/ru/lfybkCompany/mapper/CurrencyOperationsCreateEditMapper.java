package ru.lfybkCompany.mapper;

import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.CurrencyOperations;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsCreateEditDto;

@Component
public class CurrencyOperationsCreateEditMapper implements Mapper<CurrencyOperationsCreateEditDto,
                                                                    CurrencyOperations>{
    @Override
    public CurrencyOperations map(CurrencyOperationsCreateEditDto entity) {
        return CurrencyOperations.builder()
                .name(entity.name())
                .build();
    }
}
