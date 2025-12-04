package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDTO {

    private Long id;

    private String mensaje;
    private String tipoNotificacion;
    private boolean leido;

    private LocalDate fechaNotificacion;

    private Long usuarioId; // FK hacia Usuario
}
