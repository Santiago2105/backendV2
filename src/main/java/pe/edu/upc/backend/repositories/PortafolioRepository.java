package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Portafolio;

import java.util.List;

public interface PortafolioRepository extends JpaRepository<Portafolio, Long> {

    // ----------------------------------------------------
    // QUERY METHODS
    // ----------------------------------------------------

    List<Portafolio> findByArtista_Id(Long artistaId);

    List<Portafolio> findByTipo(String tipo);

    List<Portafolio> findByTitulo(String titulo);


    // ----------------------------------------------------
    // SQL NATIVO
    // ----------------------------------------------------

    @Query(value = "SELECT * FROM portafolios WHERE artista_id = ?1", nativeQuery = true)
    List<Portafolio> findByArtistaSQL(Long artistaId);


    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Query("SELECT p FROM Portafolio p WHERE p.artista.id = ?1")
    List<Portafolio> findByArtistaJPQL(Long artistaId);
}
