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
@Table(name = "reseñas")   // La tabla sí mantiene la ñ
public class Resenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reseñas")   // La columna también mantiene ñ
    private Long id;

    private String puntuacion;  // CHAR(1)

    @Column(length = 500)
    private String comentario;

    @Column(name = "fecha_reseña")
    private LocalDate fechaResenia;

    // RELACIÓN CON EVENTO
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_eventos")
    private Evento evento;

    // RELACIÓN CON RESTAURANTE
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_restaurantes")
    private Restaurante restaurante;

    // RELACIÓN CON ARTISTA
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artistas")
    private Artista artista;
}

