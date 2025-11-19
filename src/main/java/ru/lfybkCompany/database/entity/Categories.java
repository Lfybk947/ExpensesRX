package ru.lfybkCompany.database.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString(of = "name")
public class Categories implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "categories_name", unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    public boolean equals(final Object o) {
        if (!(o instanceof Categories that)) return false;
        return getId() != null && getId().equals(that.getId());
    }

    public int hashCode() {
        return Categories.class.hashCode();
    }

}
