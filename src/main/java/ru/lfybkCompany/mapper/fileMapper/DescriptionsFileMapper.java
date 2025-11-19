package ru.lfybkCompany.mapper.fileMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.dto.createReadDto.DescriptionsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.DescriptionsReadDto;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.mapper.Mapper;
import ru.lfybkCompany.service.entityService.DescriptionsService;
import ru.lfybkCompany.service.entityService.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DescriptionsFileMapper implements Mapper<List<List<String>>, List<DescriptionsCreateEditDto>> {
    private final DescriptionsService descriptionsService;
    private final UserService userService;

    @Override
    public List<DescriptionsCreateEditDto> map(List<List<String>> entity) {
        try {
            var user = userService.getAuthorizationUser().orElseThrow();
            List<DescriptionsReadDto> dDto = descriptionsService.findAllByUserFromMapper(user);

            return entity.stream()
                    .map((list) -> list.get(4))
                    .distinct()
                    .filter((de)-> dDto.stream().noneMatch((d)-> de.equals(d.name())))
                    .map((name)-> new DescriptionsCreateEditDto(name, user.id()))
                    .toList();// O(N)

        } catch (FileProcessingException e) {
            throw new FileProcessingException(("Failed mapping to DescriptionsCreateEditDto :%S").formatted(e.getMessage()));
        }
    }
}
