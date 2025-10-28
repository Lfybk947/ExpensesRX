package ru.lfybkCompany.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.dto.createReadDto.ExpensesCreateEditDto;

import static org.springframework.util.StringUtils.hasText;

@Component
public class ExpensesInfoValidator implements ConstraintValidator<ExpensesInfo, ExpensesCreateEditDto> {

    @Override
    public boolean isValid(ExpensesCreateEditDto value, ConstraintValidatorContext context) {
        return hasText(String.valueOf(value.categoriesID())) || hasText(String.valueOf(value.descriptionsID()));
    }
}
