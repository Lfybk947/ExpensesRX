package ru.lfybkCompany.mapper;

import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.Descriptions;
import ru.lfybkCompany.dto.createReadDto.DescriptionsCreateEditDto;

@Component
public class DescriptionsCreateEditMapper implements Mapper<DescriptionsCreateEditDto, Descriptions>{
    @Override
    public Descriptions map(DescriptionsCreateEditDto entity) {
        return Descriptions.builder()
                .name(entity.name())
                .build();
    }
}
