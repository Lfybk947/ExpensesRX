package ru.lfybkCompany.database.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Gender {
    MALE, FEMALE;

    public Optional<Gender> find(String gender) {
        return Arrays.stream(values())
                .filter(it-> it.name().equals(gender))
                .findFirst();
    }



}
