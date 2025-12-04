package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnuncioDTO {

    private Long id;

    private String titulo;
    private String descripcion;
    private String generoBuscado;
    private String ubicacion;
    private String presupuesto;

    private boolean activo;

    private LocalDate fechaCreacion;
    private LocalDate fechaEvento;

    private Long eventoId; // FK hacia Evento
}
