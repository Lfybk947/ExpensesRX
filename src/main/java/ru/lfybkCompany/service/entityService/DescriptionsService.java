package ru.lfybkCompany.service.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.database.repository.DescriptionsRepository;
import ru.lfybkCompany.dto.createReadDto.DescriptionsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.DescriptionsReadDto;
import ru.lfybkCompany.dto.createReadDto.UserReadDto;
import ru.lfybkCompany.mapper.DescriptionsCreateEditMapper;
import ru.lfybkCompany.mapper.DescriptionsReadMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DescriptionsService {
    private final DescriptionsRepository descriptionsRepository;
    private final DescriptionsReadMapper descriptionsReadMapper;
    private final DescriptionsCreateEditMapper descriptionsCreateEditMapper;

    public List<DescriptionsReadDto> findAll() {
        return descriptionsRepository.findAll()
                .stream().map(descriptionsReadMapper::map).toList();
    }

    public List<DescriptionsReadDto> findAllByUser(UserReadDto user) {
        return user.role().equals(Role.ADMIN) ? findAll() : findAllByUserFromMapper(user);
    }

    public List<DescriptionsReadDto> findAllByUserFromMapper(UserReadDto user) {
        return descriptionsRepository.findAllByUserId(user.id())
                .stream().map(descriptionsReadMapper::map).toList();
    }

    @Transactional
    public List<DescriptionsReadDto> create(List<DescriptionsCreateEditDto> createEditDto) {
        return createEditDto.stream()
                .map(descriptionsCreateEditMapper::map)
                .map(descriptionsRepository::save)
                .map(descriptionsReadMapper::map)
                .toList();
    }
}
