package com.amr.TaskManager.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private String message;
    private String description;
    private LocalDateTime timeStamp;
}
