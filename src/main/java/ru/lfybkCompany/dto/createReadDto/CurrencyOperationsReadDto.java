package ru.lfybkCompany.dto.createReadDto;


import ru.lfybkCompany.database.entity.User;

public record CurrencyOperationsReadDto(Integer id, String name, User user) {
}
