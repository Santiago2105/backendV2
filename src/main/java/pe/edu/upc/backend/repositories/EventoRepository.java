package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backend.entities.Evento;

import java.time.LocalDate;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Query Method
    List<Evento> findByRestaurante_Id(Long restauranteId);

    List<Evento> findByArtista_Id(Long artistaId);

    List<Evento> findByFechaEvento(LocalDate fechaEvento);


    @Query(value = "SELECT * FROM eventos WHERE id_restaurantes = :id", nativeQuery = true)
    List<Evento> findByRestauranteSQL(@Param("id") Long restauranteId);

    @Query("SELECT e FROM Evento e WHERE e.restaurante.id = :id")
    List<Evento> findByRestauranteJPQL(@Param("id") Long restauranteId);
}
