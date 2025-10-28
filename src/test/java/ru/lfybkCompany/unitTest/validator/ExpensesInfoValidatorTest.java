package ru.lfybkCompany.unitTest.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lfybkCompany.dto.createReadDto.ExpensesCreateEditDto;
import ru.lfybkCompany.validator.ExpensesInfoValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ExpensesInfoValidatorTest {
    @Mock
    private ConstraintValidatorContext context;
    @InjectMocks
    private ExpensesInfoValidator expensesInfoValidator;

    ExpensesCreateEditDto expensesCreateEditDto = new ExpensesCreateEditDto(
            LocalDateTime.of(2025, 10, 12, 1, 1, 1, 1),
            BigDecimal.valueOf(123L),
            1,
            1L,
            1L,
            1L
    );

    @Test
    public void test_isValid_validData() {
        assertTrue(expensesInfoValidator.isValid(expensesCreateEditDto, context));
    }
}
