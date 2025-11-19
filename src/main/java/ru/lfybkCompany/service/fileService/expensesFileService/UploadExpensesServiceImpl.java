package ru.lfybkCompany.service.fileService.expensesFileService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.lfybkCompany.dto.fileDto.FileInfoDto;
import ru.lfybkCompany.exception.*;
import ru.lfybkCompany.mapper.fileMapper.CategoriesFileMapper;
import ru.lfybkCompany.mapper.fileMapper.CurrencyOperationsFileMapper;
import ru.lfybkCompany.mapper.fileMapper.DescriptionsFileMapper;
import ru.lfybkCompany.mapper.fileMapper.ExpensesFileMapper;
import ru.lfybkCompany.service.entityService.*;
import ru.lfybkCompany.service.fileService.FileService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadExpensesServiceImpl implements UploadExpensesService {
    private final ExpensesService expensesService;
    private final CategoriesService categoriesService;
    private final CurrencyOperationsService currencyOperationsService;
    private final DescriptionsService descriptionsService;
    private final UserService userService;

    private final FileService fileServiceImpl;
    private final FilterExpensesFileImpl filterExpensesFileImpl;

    private final CurrencyOperationsFileMapper currencyOperationsFileMapper;
    private final CategoriesFileMapper categoriesFileMapper;
    private final DescriptionsFileMapper descriptionsFileMapper;
    private final ExpensesFileMapper expensesFileMapper;

    @Value(value = "#{\"${app.upload.content_type}\".split(',')}")
    private List<String> ALLOWED_CONTENT_TYPE;


    public String uploadFileData(FileInfoDto file) throws FileUploadException, FileDownloadException, FileProcessingException {
        List<String> resource = fileServiceImpl.get(file.getKey());

        if (resource == null || resource.isEmpty()) {
            throw new EmptyFileException("File is empty");
        }

        if (!ALLOWED_CONTENT_TYPE.contains(file.getName().substring(file.getName().lastIndexOf(".")+1))) {
            throw new InvalidFileTypeException(String.format("Unsupported file type. Supported file: %S",
                    String.join(", ", ALLOWED_CONTENT_TYPE)));
        }
        var uniqueList = filterExpensesFileImpl.checkUnique(resource);

        currencyOperationsService.create(currencyOperationsFileMapper.map(uniqueList));
        categoriesService.create(categoriesFileMapper.map(uniqueList));
        descriptionsService.create(descriptionsFileMapper.map(uniqueList));

        expensesService.create(expensesFileMapper.map(uniqueList));

        return file.getName();

    }
}
