package ru.lfybkCompany.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.*;
import ru.lfybkCompany.database.repository.CategoriesRepository;
import ru.lfybkCompany.database.repository.CurrencyOperationsRepository;
import ru.lfybkCompany.database.repository.DescriptionsRepository;
import ru.lfybkCompany.database.repository.UserRepository;
import ru.lfybkCompany.dto.createReadDto.ExpensesCreateEditDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExpensesCreateEditMapper implements Mapper<ExpensesCreateEditDto, Expenses> {
    private final CurrencyOperationsRepository currencyOperationsRepository;
    private final CategoriesRepository categoriesRepository;
    private final DescriptionsRepository descriptionsRepository;
    private final UserRepository userRepository;

    @Override
    public Expenses map(ExpensesCreateEditDto entity) {
        Expenses expenses = new Expenses();
        copy(entity, expenses);
        return expenses;
    }

    @Override
    public Expenses map(ExpensesCreateEditDto fromObject, Expenses toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ExpensesCreateEditDto fromObject, Expenses toObject) {
        toObject.setDate(fromObject.date());
        toObject.setSum(fromObject.sum());
        toObject.setCurrencyOperations(getCurrencyOperations(fromObject.currencyOperationsID()));
        toObject.setCategories(getCategories(fromObject.categoriesID()));
        toObject.setDescriptions(getDescriptions(fromObject.descriptionsID()));
        toObject.setUser(getUser(fromObject.userID()));
    }

    private CurrencyOperations getCurrencyOperations(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(currencyOperationsRepository::findById)
                .orElse(null);
    }
    private Categories getCategories(Long id) {
        return Optional.ofNullable(id)
                .flatMap(categoriesRepository::findById)
                .orElse(null);
    }
    private Descriptions getDescriptions(Long id) {
        return Optional.ofNullable(id)
                .flatMap(descriptionsRepository::findById)
                .orElse(null);
    }
    private User getUser(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElse(null);
    }


}
