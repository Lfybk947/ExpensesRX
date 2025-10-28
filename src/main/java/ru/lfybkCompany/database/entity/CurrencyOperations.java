package ru.lfybkCompany.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
//@EqualsAndHashCode(of = "name")
@ToString(of = "name")
public class CurrencyOperations implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    public boolean equals(final Object o) {
        if (!(o instanceof CurrencyOperations that)) return false;
        return getId() != null && getId().equals(that.getId());
    }

    public int hashCode() {
        return CurrencyOperations.class.hashCode();
    }
}
