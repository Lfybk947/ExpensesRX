package ru.lfybkCompany.mapper.fileMapper;

import org.springframework.stereotype.Component;
import ru.lfybkCompany.database.entity.FileInfo;
import ru.lfybkCompany.dto.fileDto.FileInfoDto;
import ru.lfybkCompany.mapper.Mapper;

@Component
public class FileInfoReadMapper implements Mapper<FileInfo, FileInfoDto> {
    @Override
    public FileInfoDto map(FileInfo entity) {
        return FileInfoDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .size(entity.getSize())
                .key(entity.getKey())
                .uploadDate(entity.getUploadDate())
                .user(entity.getUser())
                .build();
    }
}
