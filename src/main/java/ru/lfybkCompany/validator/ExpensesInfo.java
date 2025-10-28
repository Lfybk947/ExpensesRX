package ru.lfybkCompany.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = {ExpensesInfoValidator.class})
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpensesInfo {

    String message() default "{jakarta.validation.constraints.ExpensesInfo.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
