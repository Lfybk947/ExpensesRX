package ru.lfybkCompany.mapper.fileMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.FileInfo;
import ru.lfybkCompany.dto.fileDto.FileInfoDto;
import ru.lfybkCompany.mapper.Mapper;

@Component
@RequiredArgsConstructor
public class FileInfoCreateEditMapper implements Mapper<FileInfoDto, FileInfo> {

    @Override
    public FileInfo map(FileInfoDto entity) {
        return FileInfo.builder()
                .name(entity.getName())
                .size(entity.getSize())
                .key(entity.getKey())
                .uploadDate(entity.getUploadDate())
                .user(entity.getUser())
                .build();
    }
}
