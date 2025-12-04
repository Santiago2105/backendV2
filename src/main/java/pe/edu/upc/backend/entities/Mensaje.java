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
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensajes")
    private Long id;

    @Column(length = 500)
    private String texto;

    @Column(name = "fecha_envio")
    private LocalDate fechaEnvio;

    // RELACIÓN CON ANUNCIO (Mensaje → Anuncio)
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_anuncios")
    private Anuncio anuncio;

    // RELACIÓN CON USUARIO (Mensaje → Usuario)
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuarios")
    private User usuario;
}

