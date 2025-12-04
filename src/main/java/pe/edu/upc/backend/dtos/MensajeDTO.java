package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensajeDTO {

    private Long id;

    private String texto;
    private LocalDate fechaEnvio;

    private Long anuncioId; // FK hacia Anuncio
    private Long usuarioId; // FK hacia Usuario
}
