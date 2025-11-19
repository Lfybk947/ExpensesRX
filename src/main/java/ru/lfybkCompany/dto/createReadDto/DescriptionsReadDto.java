package ru.lfybkCompany.dto.createReadDto;


import ru.lfybkCompany.database.entity.User;

public record DescriptionsReadDto(Long id, String name, User user) {
}
