package ru.lfybkCompany.service.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lfybkCompany.database.repository.CategoriesRepository;
import ru.lfybkCompany.dto.createReadDto.CategoriesCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.CategoriesReadDto;
import ru.lfybkCompany.mapper.CategoriesCreateEditMapper;
import ru.lfybkCompany.mapper.CategoriesReadMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final CategoriesReadMapper categoriesReadMapper;
    private final CategoriesCreateEditMapper categoriesCreateEditMapper;

    public List<CategoriesReadDto> findAll() {
        return categoriesRepository.findAll()
                .stream().map(categoriesReadMapper::map).toList();
    }

    @Transactional
    public List<CategoriesReadDto> create(List<CategoriesCreateEditDto> createEditDto) {
        return createEditDto.stream()
                .map(categoriesCreateEditMapper::map)
                .map(categoriesRepository::save)
                .map(categoriesReadMapper::map)
                .toList();
    }
}
