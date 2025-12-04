package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoporteDTO {

    private Long id;

    private String asunto;
    private String descripcion;

    private boolean estado;

    private LocalDate fechaCreacion;
    private LocalDate fechaCierre;

    private Long usuarioId; // FK hacia Usuario
}
