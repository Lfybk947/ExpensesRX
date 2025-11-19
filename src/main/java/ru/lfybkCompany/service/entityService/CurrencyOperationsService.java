package ru.lfybkCompany.service.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.database.repository.CurrencyOperationsRepository;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.CurrencyOperationsReadDto;
import ru.lfybkCompany.dto.createReadDto.UserReadDto;
import ru.lfybkCompany.mapper.CurrencyOperationsCreateEditMapper;
import ru.lfybkCompany.mapper.CurrencyOperationsReadMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurrencyOperationsService {
    private final CurrencyOperationsRepository currencyOperationsRepository;
    private final CurrencyOperationsCreateEditMapper currencyOperationsCreateEditMapper;
    private final CurrencyOperationsReadMapper currencyOperationsReadMapper;

    public List<CurrencyOperationsReadDto> findAll() {
        return currencyOperationsRepository.findAll().stream()
                .map(currencyOperationsReadMapper::map).toList();
    }

    public List<CurrencyOperationsReadDto> findAllByUser(UserReadDto user) {
        return user.role().equals(Role.ADMIN) ? findAll() : findAllByUserFromMapper(user);
    }

    public List<CurrencyOperationsReadDto> findAllByUserFromMapper(UserReadDto user) {
        return currencyOperationsRepository.findAllByUserId(user.id())
                .stream().map(currencyOperationsReadMapper::map).toList();
    }

    @Transactional
    public List<CurrencyOperationsReadDto> create(List<CurrencyOperationsCreateEditDto> createEditDto) {
        return createEditDto.stream()
                .map(currencyOperationsCreateEditMapper::map)
                .map(currencyOperationsRepository::save)
                .map(currencyOperationsReadMapper::map)
                .toList();
    }

}
