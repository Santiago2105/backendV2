package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Resenia;

import java.util.List;

public interface ReseniaRepository extends JpaRepository<Resenia, Long> {

    // Query Method
    List<Resenia> findByEvento_Id(Long eventoId);

    List<Resenia> findByArtista_Id(Long artistaId);

    List<Resenia> findByRestaurante_Id(Long restauranteId);


    // SQL Nativo
    @Query(value = "SELECT * FROM reseñas WHERE id_eventos = ?1", nativeQuery = true)
    List<Resenia> findByEventoSQL(Long eventoId);


    // JPQL
    @Query("SELECT r FROM Resenia r WHERE r.evento.id = ?1")
    List<Resenia> findByEventoJPQL(Long eventoId);
}
