package ru.lfybkCompany.database.repository.fileRepository;

import ru.lfybkCompany.database.entity.FileInfo;

import java.util.Optional;

public interface FileRepository {
    FileInfo create(FileInfo fileInfo);

    Optional<FileInfo> findById(Long id);
}
