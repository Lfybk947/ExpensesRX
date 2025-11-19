package ru.lfybkCompany.mapper.fileMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.dto.createReadDto.CategoriesCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.CategoriesReadDto;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.mapper.Mapper;
import ru.lfybkCompany.service.entityService.CategoriesService;
import ru.lfybkCompany.service.entityService.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoriesFileMapper implements
        Mapper<List<List<String>>, List<CategoriesCreateEditDto>> {
    private final CategoriesService categoriesService;
    private final UserService userService;

    @Override
    public List<CategoriesCreateEditDto> map(List<List<String>> entity) {
        try {
            var user = userService.getAuthorizationUser().orElseThrow();
            List<CategoriesReadDto> cDto = categoriesService.findAllByUserFromMapper(user);

            return entity.stream()
                    .map((list) -> list.get(3))
                    .distinct()
                    .filter((ca)-> cDto.stream().noneMatch((c)-> ca.contains(c.name())))
                    .map((name)-> new CategoriesCreateEditDto(name, user.id()))
                    .toList();// O(N)

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Failed mapping to CategoriesCreateEditDto :%S")
                    .formatted(e.getMessage()));
        }
    }
}
