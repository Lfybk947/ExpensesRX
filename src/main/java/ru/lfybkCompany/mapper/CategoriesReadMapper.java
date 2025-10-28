package ru.lfybkCompany.mapper;

import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.Categories;
import ru.lfybkCompany.dto.createReadDto.CategoriesReadDto;

@Component
public class CategoriesReadMapper implements Mapper<Categories, CategoriesReadDto>{
    @Override
    public CategoriesReadDto map(Categories entity) {
        return new CategoriesReadDto(
                entity.getId(),
                entity.getName());
    }
}
