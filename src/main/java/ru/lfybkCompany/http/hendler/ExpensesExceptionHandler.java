package ru.lfybkCompany.http.hendler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import ru.lfybkCompany.dto.responseDto.ErrorResponse;
import ru.lfybkCompany.exception.FileUploadException;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice(basePackages = "ru.lfybkCompany.http.rest")
public class ExpensesExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handlerFileUploadException(FileUploadException ex) {
        log.error(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "FILE_UPLOAD_ERROR",
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> handlerFileUploadException(MultipartException ex) {
        log.error(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "MULTIPART_ERROR",
                "Error upload file: " + ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
