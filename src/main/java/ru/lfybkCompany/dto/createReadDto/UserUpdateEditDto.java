package ru.lfybkCompany.dto.createReadDto;

import jakarta.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;

import java.time.LocalDate;

public record UserUpdateEditDto(
        @NotNull(message = "{user.name.notNull}") String name,
        @NotNull(message = "{user.lastname.notNull}") String lastName,
        @NotBlank(message = "{user.email.notBlank}")
        @Email(message = "{user.email.incorrectEmail}") String username,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @PastOrPresent(message = "{user.birthdate.PastOrPresent}")
        @NotNull(message = "{user.birthdate.NotNull}") LocalDate birthDate,
        @NotNull(message = "{user.role.notNull}") Role role,
        @NotNull(message = "{user.gender.notNull}") Gender gender) {

}
