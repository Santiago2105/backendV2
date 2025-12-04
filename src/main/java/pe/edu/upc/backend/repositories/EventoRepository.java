package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Evento;

import java.time.LocalDate;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Query Method
    List<Evento> findByRestaurante_Id(Long restauranteId);

    List<Evento> findByArtista_Id(Long artistaId);

    List<Evento> findByFechaEvento(LocalDate fechaEvento);


    // SQL Nativo
    @Query(value = "SELECT * FROM eventos WHERE id_restaurantes = ?1", nativeQuery = true)
    List<Evento> findByRestauranteSQL(Long restauranteId);


    // JPQL
    @Query("SELECT e FROM Evento e WHERE e.restaurante.id = ?1")
    List<Evento> findByRestauranteJPQL(Long restauranteId);
}
