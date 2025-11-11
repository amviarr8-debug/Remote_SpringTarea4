package com.amelia.demonotes2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Mapea al código 409
public class ConcurrencyConflictException extends RuntimeException {
    public ConcurrencyConflictException(String resource) {
        super("Conflicto de concurrencia al actualizar: " + resource);
    }
}
