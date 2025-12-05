package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {

    private Long id;

    private LocalDate fechaEvento;
    private String cachet;
    private boolean realizado;
    private LocalDate fechaCreacion;

    private Long restauranteId;  // FK hacia Restaurante
}
