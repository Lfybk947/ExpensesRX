package ru.lfybkCompany.dto.fileDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.lfybkCompany.database.entity.User;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@Setter
@ToString(of = {"name", "size", "key"})
public class FileInfoDto {

    private Long id;

    private String name;

    private Long size;

    private String key;

    private LocalDate uploadDate;

    private User user;
}
