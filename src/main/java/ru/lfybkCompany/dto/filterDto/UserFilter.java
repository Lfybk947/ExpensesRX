package ru.lfybkCompany.dto.filterDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;

import java.time.LocalDate;

public record UserFilter (

    @Pattern(regexp = "^[a-zA-Z0-9]*", message = "{user.field.Pattern}")
    String name,

    @Pattern(regexp = "^[a-zA-Z0-9]*", message = "{user.field.Pattern}")
    String lastName,

    @PastOrPresent(message = "{user.birthdate.PastOrPresent}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate,

    @Email
    String username,

    Role role,

    Gender gender
) {
}
