package ru.lfybkCompany.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.dto.filterDto.ExpensesFilter;
import ru.lfybkCompany.dto.filterDto.ExpensesSessionFilter;

@Component
@RequiredArgsConstructor
public class FilterExpensesMapper implements Mapper<ExpensesSessionFilter, ExpensesFilter>{

    @Override
    public ExpensesFilter map(ExpensesSessionFilter entity) {
        return new ExpensesFilter(
                    entity.getFromDate(),
                    entity.getToDate(),
                    entity.getFromSum(),
                    entity.getToSum(),
                    entity.getCurrencyOperations(),
                    entity.getCategories(),
                    entity.getDescriptions(),
                    entity.getUsers()
        );
    }

}
