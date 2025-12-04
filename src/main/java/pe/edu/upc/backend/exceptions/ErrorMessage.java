package pe.edu.upc.backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private int status;
    private String exception;
    private String message;
    private String description;
    private LocalDateTime timestamp;

}
