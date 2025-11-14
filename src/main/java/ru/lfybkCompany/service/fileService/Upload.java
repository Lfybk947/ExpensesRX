package ru.lfybkCompany.service.fileService;


public interface Upload <F, C, T, D>{
    void upload(F filePath, C content);

    T get(F filePath);

    D download(F filePath);
}
