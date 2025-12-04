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
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_eventos")
    private Long id;

    @Column(name = "fecha_evento")
    private LocalDate fechaEvento;

    private String cachet;  // CHAR(10) en tu modelo

    private boolean realizado;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    // RELACIÓN CON RESTAURANTE (Restaurante → Evento)
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_restaurantes")
    private Restaurante restaurante;

    // RELACIÓN CON ARTISTA (Evento → Artista invitado)
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_artistas")
    private Artista artista;
}

