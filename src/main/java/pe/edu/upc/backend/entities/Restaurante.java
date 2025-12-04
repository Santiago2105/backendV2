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
@Table(name = "restaurantes")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurantes")
    private Long id;

    private String nombre;
    private String direccion;
    private String ciudad;

    @Column(name = "aforo_mesas")
    private Integer aforoMesas;

    // RELACIÓN MANY-TO-ONE IGUAL QUE MAJOR → FACULTY
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuarios")
    private User usuario;  // dueño del restaurante
}

