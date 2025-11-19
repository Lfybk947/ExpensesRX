package ru.lfybkCompany.service.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.database.repository.CategoriesRepository;
import ru.lfybkCompany.dto.createReadDto.CategoriesCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.CategoriesReadDto;
import ru.lfybkCompany.dto.createReadDto.UserReadDto;
import ru.lfybkCompany.mapper.CategoriesCreateEditMapper;
import ru.lfybkCompany.mapper.CategoriesReadMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final CategoriesReadMapper categoriesReadMapper;
    private final CategoriesCreateEditMapper categoriesCreateEditMapper;

    public List<CategoriesReadDto> findAll() {
        return categoriesRepository.findAll()
                .stream().map(categoriesReadMapper::map).toList();
    }

    public List<CategoriesReadDto> findAllByUser(UserReadDto user) {
        return user.role().equals(Role.ADMIN) ? findAll() : findAllByUserFromMapper(user);
    }

    public List<CategoriesReadDto> findAllByUserFromMapper(UserReadDto user) {
        return categoriesRepository.findAllByUserId(user.id())
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
