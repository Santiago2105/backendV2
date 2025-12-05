package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.backend.entities.Evento;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Buscar por restaurante (propiedad restaurante en Evento)
    List<Evento> findByRestaurante_Id(Long restauranteId);

    // Buscar por fecha
    List<Evento> findByFechaEvento(LocalDate fechaEvento);

    // SQL nativo (si ya lo tenías)
    @Query(
            value = "SELECT * FROM eventos e WHERE e.id_restaurantes = ?1",
            nativeQuery = true
    )
    List<Evento> findByRestauranteSQL(Long restauranteId);

    // JPQL (si ya lo tenías)
    @Query("SELECT e FROM Evento e WHERE e.restaurante.id = :restauranteId")
    List<Evento> findByRestauranteJPQL(Long restauranteId);

}
