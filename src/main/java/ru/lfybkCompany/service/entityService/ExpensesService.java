package ru.lfybkCompany.service.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lfybkCompany.database.repository.ExpensesRepository;
import ru.lfybkCompany.dto.createReadDto.ExpensesCreateEditDto;
import ru.lfybkCompany.dto.createReadDto.ExpensesReadDto;
import ru.lfybkCompany.dto.filterDto.ExpensesFilter;
import ru.lfybkCompany.dto.filterDto.ExpensesSessionFilter;
import ru.lfybkCompany.mapper.ExpensesCreateEditMapper;
import ru.lfybkCompany.mapper.ExpensesReadMapper;
import ru.lfybkCompany.mapper.FilterExpensesMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpensesService {
    private final ExpensesRepository expensesRepository;
    private final ExpensesReadMapper expensesReadMapper;
    private final ExpensesCreateEditMapper expensesCreateEditMapper;
    private final FilterExpensesMapper filterExpensesMapper;

    public List<ExpensesReadDto> findAll() {
        return expensesRepository.findAll().stream()
                .map(expensesReadMapper::map).toList();
    }

    public List<ExpensesReadDto> findAllExpensesByUserId(Long id) {
        return expensesRepository.findAllExpensesByUserId(id).stream()
                .map(expensesReadMapper::map).toList();
    }

    @Transactional
    public List<ExpensesReadDto> create(List<ExpensesCreateEditDto> expensesDto) {
        return expensesDto.stream().map((dto)-> Optional.of(dto)
                .map(expensesCreateEditMapper::map)
                .map(expensesRepository::save)
                .map(expensesReadMapper::map)
                .orElseThrow()).toList();
    }

    public List<ExpensesReadDto> findAllByFilter(ExpensesSessionFilter filter) {
         return expensesRepository.findAllFilter(filterExpensesMapper.map(filter)).stream()
                //logic sum
                .map(expensesReadMapper::map).toList();
    }



}
