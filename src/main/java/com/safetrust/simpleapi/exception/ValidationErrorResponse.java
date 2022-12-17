package com.safetrust.simpleapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ValidationErrorResponse {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;
    private String path;
    private String appErrorCode;
    private List<Violation> errors = new ArrayList<>();

    public void processMessages() {
        this.message = errors.stream().map(Violation::getMessage).collect(Collectors.joining(" "));
    }
}