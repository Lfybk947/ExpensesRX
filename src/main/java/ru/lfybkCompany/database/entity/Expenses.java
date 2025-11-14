package ru.lfybkCompany.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = {"date", "sum"})
public class Expenses implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_operations", nullable = false)
    private LocalDateTime date;

    @Column(name = "sum_operations")
    private BigDecimal sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_operations_id")
    private CurrencyOperations currencyOperations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_id")
    private Categories categories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "descriptions_id")
    private Descriptions descriptions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    public boolean equals(final Object o) {
        if (!(o instanceof Expenses that)) return false;
        return getId() != null && getId().equals(that.getId());
    }

    public int hashCode() {
        return Expenses.class.hashCode();
    }
}
