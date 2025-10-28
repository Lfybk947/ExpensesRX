package ru.lfybkCompany.http.hendler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
//@ControllerAdvice(basePackages = "ru.lfybkCompany.http.controller")
public class ControllerHandler {

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception exception, HttpServletRequest request) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }
}
