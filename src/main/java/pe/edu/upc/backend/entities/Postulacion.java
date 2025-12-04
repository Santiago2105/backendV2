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
@Table(name = "postulaciones")
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postulaciones")
    private Long id;

    @Column(length = 500)
    private String mensaje;

    private boolean aceptada;

    @Column(name = "fecha_postulacion")
    private LocalDate fechaPostulacion;

    // RELACIÓN CON ANUNCIO (Postulación → Anuncio)
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_anuncios")
    private Anuncio anuncio;

    // RELACIÓN CON ARTISTA (Postulación → Artista)
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artistas")
    private Artista artista;
}

