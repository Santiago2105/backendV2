package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReseniaDTO {

    private Long id;

    private String puntuacion;
    private String comentario;
    private LocalDate fechaResenia;

    private Long eventoId;       // FK hacia Evento
    private Long artistaId;      // FK hacia Artista
    private Long restauranteId;  // FK hacia Restaurante
}
