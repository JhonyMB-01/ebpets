package org.ebpts.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private String detalle;
    private int status;
    private LocalDateTime timestamp;
}
