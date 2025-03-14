package br.com.nlw.events.extensions;

import br.com.nlw.events.DTO.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorBuilderExtensions {
    public static ResponseEntity<ErrorMessageDTO> buildErrorResponse(Exception ex, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorMessageDTO(ex.getMessage()));
    }
}
