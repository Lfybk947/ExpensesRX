package ru.lfybkCompany.service.fileUploadService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.lfybkCompany.exception.EmptyFileException;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.exception.FileUploadException;
import ru.lfybkCompany.exception.InvalidFileTypeException;
import ru.lfybkCompany.service.entityService.CategoriesService;
import ru.lfybkCompany.service.entityService.CurrencyOperationsService;
import ru.lfybkCompany.service.entityService.DescriptionsService;
import ru.lfybkCompany.service.entityService.ExpensesService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadToDBService {
    private final ExpensesService expensesService;
    private final FileUploadService uploadFileService;
    private final MapperFIleToDtoService mapperFIleToDtoService;

    private final CategoriesService categoriesService;
    private final CurrencyOperationsService currencyOperationsService;
    private final DescriptionsService descriptionsService;

    private static final List<String> ALLOWED_CONTENT_TYPE = Arrays.asList("text/csv", "text/ofx");


    public String uploadExpensesFileData(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new EmptyFileException("File is empty");
        }
        if (!ALLOWED_CONTENT_TYPE.contains(file.getContentType())) {
            throw new InvalidFileTypeException(String.format("Unsupported file type. Supported file: %S",
                    String.join(", ", ALLOWED_CONTENT_TYPE)));
        }

        try {

            uploadFileService.upload(file.getOriginalFilename(), file.getInputStream());
            mapperFIleToDtoService.getExpensesFile(file.getOriginalFilename());

            currencyOperationsService.create(mapperFIleToDtoService.createCOced());
            categoriesService.create(mapperFIleToDtoService.createCced());
            descriptionsService.create(mapperFIleToDtoService.createDced());

            expensesService.create(mapperFIleToDtoService.createEced());

            return file.getOriginalFilename();

        } catch (FileProcessingException | FileUploadException | IOException e) {
            throw new FileProcessingException(("Incorrect file: %S").formatted(e.getMessage()));
        }

    }
}
