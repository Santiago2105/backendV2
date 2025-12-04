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
@Table(name = "portafolios")
public class Portafolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_portafolios")
    private Long id;

    private String tipo;

    private String url;

    private String titulo;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    // RELACIÓN CON ARTISTA (Portafolio → Artista)
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artistas")
    private Artista artista;
}

