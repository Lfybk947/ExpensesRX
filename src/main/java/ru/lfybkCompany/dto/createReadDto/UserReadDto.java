package ru.lfybkCompany.dto.createReadDto;

import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;

import java.time.LocalDate;


public record UserReadDto(
        Long id,
        String name,
        String lastName,
        String username,
        LocalDate birthDate,
        Role role,
        Gender gender) {
}
