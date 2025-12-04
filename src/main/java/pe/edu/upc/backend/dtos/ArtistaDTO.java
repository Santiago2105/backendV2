package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistaDTO {

    private Long id;
    private String nombreArtistico;
    private String generoPrincipal;
    private String bio;
    private String ciudad;

    private Long usuarioId; // FK de usuario
}
