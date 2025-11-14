package ru.lfybkCompany.service.fileService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.lfybkCompany.database.repository.fileRepository.FileRepository;
import ru.lfybkCompany.database.repository.fileRepository.FileRepositoryImpl;
import ru.lfybkCompany.dto.fileDto.FileInfoDto;
import ru.lfybkCompany.exception.FileDownloadException;
import ru.lfybkCompany.exception.FileUploadException;
import ru.lfybkCompany.mapper.UserCreateReadMapper;
import ru.lfybkCompany.service.entityService.UserService;
import ru.lfybkCompany.mapper.fileMapper.FileInfoCreateEditMapper;
import ru.lfybkCompany.mapper.fileMapper.FileInfoReadMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    private final FileRepository fileRepository;
    private final FileUpload fileUploadService;
    private final UserService userService;
    private final UserCreateReadMapper userCreateReadMapper;
    private final FileInfoCreateEditMapper fileInfoCreateEditMapper;
    private final FileInfoReadMapper fileInfoReadMapper;

    @Transactional(rollbackFor = {FileUploadException.class, IOException.class})
    @Override
    public FileInfoDto upload(MultipartFile resource) {
        try {
            String key = generatedKey(resource.getOriginalFilename());
            FileInfoDto createdFileDto = FileInfoDto.builder()
                    .name(resource.getOriginalFilename())
                    .key(key)
                    .size(resource.getSize())
                    .user(userService.getAuthorizationUser().map(userCreateReadMapper::map).orElseThrow())
                    .build();

            createdFileDto = Optional.of(createdFileDto)
                    .map(fileInfoCreateEditMapper::map)
                    .map(fileRepository::create)
                    .map(fileInfoReadMapper::map)
                    .orElseThrow();

            fileUploadService.upload(key, resource.getInputStream());
            return createdFileDto;
        } catch (FileUploadException | IOException e) {
            throw new FileUploadException(("Failed upload file :%S").formatted(e.getMessage()));
        }
    }

    @Override
    public Resource download(String key) throws FileDownloadException {
        return fileUploadService.download(key);
    }

    @Override
    public List<String> get(String key) {
        return fileUploadService.get(key);
    }

    @Transactional
    @Override
    public FileInfoDto findById(Long id) {
        return fileRepository.findById(id).map(fileInfoReadMapper::map).orElseThrow();
    }

    private String generatedKey(String name) {
        return DigestUtils.md5DigestAsHex((name + LocalDateTime.now().toString()).getBytes(StandardCharsets.UTF_8));
    }
}
