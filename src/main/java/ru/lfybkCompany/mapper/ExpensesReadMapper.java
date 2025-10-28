package ru.lfybkCompany.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.Expenses;
import ru.lfybkCompany.dto.createReadDto.*;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExpensesReadMapper implements Mapper<Expenses, ExpensesReadDto>{
    private final CurrencyOperationsReadMapper currencyOperationsReadMapper;
    private final CategoriesReadMapper categoriesReadMapper;
    private final DescriptionsReadMapper descriptionsReadMapper;
    private final UserReadMapper userReadMapper;

    @Override
    public ExpensesReadDto map(Expenses entity) {
        CurrencyOperationsReadDto currencyOperations = Optional.ofNullable(entity.getCurrencyOperations())
                .map(currencyOperationsReadMapper::map).orElse(null);
        CategoriesReadDto categories = Optional.ofNullable(entity.getCategories())
                .map(categoriesReadMapper::map).orElse(null);
        DescriptionsReadDto descriptions = Optional.ofNullable(entity.getDescriptions())
                .map(descriptionsReadMapper::map).orElse(null);
        UserReadDto user = Optional.ofNullable(entity.getUser())
                .map(userReadMapper::map).orElse(null);

        return new ExpensesReadDto(
                entity.getId(),
                entity.getDate(),
                entity.getSum(),
                currencyOperations,
                categories,
                descriptions,
                user
        );
    }
}
