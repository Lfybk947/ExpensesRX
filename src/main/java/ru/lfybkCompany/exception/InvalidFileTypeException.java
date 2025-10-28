package ru.lfybkCompany.exception;

public class InvalidFileTypeException extends FileUploadException{
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
