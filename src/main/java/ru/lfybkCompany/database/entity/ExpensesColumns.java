package ru.lfybkCompany.database.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ExpensesColumns {

    DATA_OPERATIONS("Date operations"),
    SUM_OPERATIONS("Sum operations"),
    CURRENCY_OPERATIONS("Currency operations"),
    CATEGORIES("Categories"),
    DESCRIPTIONS("Descriptions");

    private final String name;

    ExpensesColumns(String name) {
        this.name = name;
    }
}
