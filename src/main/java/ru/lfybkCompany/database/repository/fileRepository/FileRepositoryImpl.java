package ru.lfybkCompany.database.repository.fileRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.lfybkCompany.database.entity.FileInfo;
import ru.lfybkCompany.database.repository.UserRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository{
    private final UserRepository userRepository;

    private static final String CREATE_FILE =
            "INSERT INTO files_info(file_name, file_size, file_key, upload_date, users_id) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_FILE_By_ID =
            "SELECT id, file_name, file_size, file_key, upload_date, users_id FROM files_info WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public FileInfo create(FileInfo fileInfo) {
        LocalDate uploadDate = LocalDate.now();
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(x-> {
            PreparedStatement preparedStatement = x.prepareStatement(CREATE_FILE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, fileInfo.getName());
            preparedStatement.setLong(2, fileInfo.getSize());
            preparedStatement.setString(3, fileInfo.getKey());
            preparedStatement.setDate(4, Date.valueOf(uploadDate));
            preparedStatement.setLong(5, fileInfo.getUser().getId());
            return preparedStatement;
        }, keyHolder);

        return fileInfo.toBuilder()
                .id((Long) Objects.requireNonNull(keyHolder.getKeys()).get("id"))//??
                .uploadDate(uploadDate)
                .build();
    }

    @Override
    public Optional<FileInfo> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_FILE_By_ID, rowMapper(), id));
    }

    private RowMapper<FileInfo> rowMapper() {
        return (rs, rowNum) -> FileInfo.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("file_name"))
                .size(rs.getLong("file_size"))
                .key(rs.getString("file_key"))
                .uploadDate(rs.getObject("upload_date", LocalDate.class))
                .user(userRepository.findUserById(rs.getLong("users_id")).orElseThrow())
                .build();
    }
}
