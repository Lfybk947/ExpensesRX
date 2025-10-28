package ru.lfybkCompany.mapper;

import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.Categories;
import ru.lfybkCompany.dto.createReadDto.CategoriesCreateEditDto;

@Component
public class CategoriesCreateEditMapper implements Mapper<CategoriesCreateEditDto, Categories> {
    @Override
    public Categories map(CategoriesCreateEditDto entity) {
        return Categories.builder()
                .name(entity.name())
                .build();
    }
}
