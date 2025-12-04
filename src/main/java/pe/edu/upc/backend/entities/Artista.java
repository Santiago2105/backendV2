package pe.edu.upc.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artistas")
    private Long id;

    @Column(name = "nombre_artistico", length = 100)
    private String nombreArtistico;

    @Column(name = "genero_principal", length = 50)
    private String generoPrincipal;

    @Column(length = 500)
    private String bio;

    private String ciudad;

    // RELACIÓN MANY-TO-ONE (igual a Major → Faculty)
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuarios")
    private User usuario; // usuario dueño del perfil del artista
}

