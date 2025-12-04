package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostulacionDTO {

    private Long id;

    private String mensaje;
    private boolean aceptada;
    private LocalDate fechaPostulacion;

    private Long anuncioId;  // FK hacia Anuncio
    private Long artistaId;  // FK hacia Artista
}
