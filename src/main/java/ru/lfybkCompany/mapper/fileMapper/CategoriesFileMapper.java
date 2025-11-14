package ru.lfybkCompany.mapper.fileMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.dto.createReadDto.CategoriesCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.CategoriesReadDto;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.mapper.Mapper;
import ru.lfybkCompany.service.entityService.CategoriesService;
import ru.lfybkCompany.service.fileService.expensesFileService.FilterExpensesFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoriesFileMapper implements
        Mapper<List<List<String>>, List<CategoriesCreateEditDto>> {
    private final CategoriesService categoriesService;

    @Override
    public List<CategoriesCreateEditDto> map(List<List<String>> entity) {
        try {
            List<CategoriesReadDto> cDto = categoriesService.findAll();

            return entity.stream()
                    .map((list) -> list.get(3))
                    .distinct()
                    .filter((ca)-> cDto.stream().noneMatch((c)-> ca.contains(c.name())))
                    .map(CategoriesCreateEditDto::new)
                    .toList();// O(N)

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Failed mapping to CategoriesCreateEditDto :%S")
                    .formatted(e.getMessage()));
        }
    }
}
