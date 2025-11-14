package ru.lfybkCompany.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "name")
@Table(name = "files_info")
public class FileInfo implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String name;

    @Column(name = "file_size", nullable = false)
    private Long size;

    @Column(name = "file_key", nullable = false, unique = true)
    private String key;

    @Column(name = "upload_date", nullable = false)
    private LocalDate uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    public boolean equals(final Object o) {
        if (!(o instanceof FileInfo that)) return false;
        return getId() != null && getId().equals(that.getId());
    }

    public int hashCode() {
        return FileInfo.class.hashCode();
    }
}
