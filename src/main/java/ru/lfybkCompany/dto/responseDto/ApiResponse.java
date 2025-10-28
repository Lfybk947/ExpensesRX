package ru.lfybkCompany.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse {
    private String message;
    private Object data;
    private LocalDateTime timestamp;

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
