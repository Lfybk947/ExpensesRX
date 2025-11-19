package ru.lfybkCompany.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = "username")
@Table(name = "users")
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_user", nullable = false)
    private String name;

    @Column(name = "last_name_user", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    public boolean equals(final Object o) {
        if (!(o instanceof User that)) return false;
        return getId() != null && getId().equals(that.getId());
    }

    public int hashCode() {
        return User.class.hashCode();
    }
}
