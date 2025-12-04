package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortafolioDTO {

    private Long id;

    private String titulo;
    private String tipo;
    private String url;

    private LocalDate fechaCreacion;

    private Long artistaId; // FK hacia Artista
}
