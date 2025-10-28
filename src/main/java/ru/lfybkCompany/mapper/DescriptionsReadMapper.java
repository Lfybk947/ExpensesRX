package ru.lfybkCompany.mapper;

import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.Descriptions;
import ru.lfybkCompany.dto.createReadDto.DescriptionsReadDto;

@Component
public class DescriptionsReadMapper implements Mapper<Descriptions, DescriptionsReadDto> {
    @Override
    public DescriptionsReadDto map(Descriptions entity) {
        return new DescriptionsReadDto(
                entity.getId(),
                entity.getName());
    }
}
