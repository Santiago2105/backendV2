package pe.edu.upc.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificaciones")
    private Long id;

    private String mensaje;

    @Column(name = "tipo_notificacion", length = 70)
    private String tipoNotificacion;

    private boolean leido;

    @Column(name = "fecha_notificacion")
    private LocalDate fechaNotificacion;

    // RELACIÓN MANY-TO-ONE IGUAL QUE MAJOR → FACULTY
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuarios")
    private User usuario;
}

