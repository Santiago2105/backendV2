package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private Integer aforoMesas;

    private Long usuarioId; // FK del usuario dueño del restaurante
}
