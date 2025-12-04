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
@Table(name = "anuncios")
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anuncios")
    private Long id;

    private String titulo;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "genero_buscado", length = 50)
    private String generoBuscado;

    @Column(name = "fecha_evento")
    private LocalDate fechaEvento;

    private String ubicacion;

    private String presupuesto; // CHAR(10)

    private boolean activo;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    // RELACIÓN CON EVENTO (Evento → Anuncio)
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_eventos")
    private Evento evento;
}

