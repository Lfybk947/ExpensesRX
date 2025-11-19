package ru.lfybkCompany.mapper;

import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.CurrencyOperations;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsReadDto;

@Component
public class CurrencyOperationsReadMapper implements Mapper<CurrencyOperations, CurrencyOperationsReadDto> {
    @Override
    public CurrencyOperationsReadDto map(CurrencyOperations entity) {
        return new CurrencyOperationsReadDto(
                entity.getId(),
                entity.getName(),
                entity.getUser());
    }
}
