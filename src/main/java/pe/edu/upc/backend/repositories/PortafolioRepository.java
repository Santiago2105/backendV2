package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backend.entities.Portafolio;

import java.util.List;

public interface PortafolioRepository extends JpaRepository<Portafolio, Long> {

    // ----------------------------------------------------
    // QUERY METHODS
    // ----------------------------------------------------

    List<Portafolio> findByArtista_Id(Long artistaId);

    List<Portafolio> findByTipo(String tipo);

    List<Portafolio> findByTitulo(String titulo);


    @Query(value = "SELECT * FROM portafolios WHERE id_artistas = :id", nativeQuery = true) // Asegura que la columna en BD sea id_artistas
    List<Portafolio> findByArtistaSQL(@Param("id") Long artistaId);

    @Query("SELECT p FROM Portafolio p WHERE p.artista.id = :id")
    List<Portafolio> findByArtistaJPQL(@Param("id") Long artistaId);
}
