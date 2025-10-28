package ru.lfybkCompany.service.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lfybkCompany.database.repository.DescriptionsRepository;
import ru.lfybkCompany.dto.createReadDto.DescriptionsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.DescriptionsReadDto;
import ru.lfybkCompany.mapper.DescriptionsCreateEditMapper;
import ru.lfybkCompany.mapper.DescriptionsReadMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DescriptionsService {
    private final DescriptionsRepository descriptionsRepository;
    private final DescriptionsReadMapper descriptionsReadMapper;
    private final DescriptionsCreateEditMapper descriptionsCreateEditMapper;

    public List<DescriptionsReadDto> findAll() {
        return descriptionsRepository.findAll()
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
