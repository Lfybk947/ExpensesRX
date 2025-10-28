package ru.lfybkCompany.exception;

public class EmptyFileException extends FileUploadException{
    public EmptyFileException(String message) {
        super(message);
    }
}
