package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Postulacion;

import java.util.List;

public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    // Query Method
    List<Postulacion> findByAnuncio_Id(Long anuncioId);

    List<Postulacion> findByArtista_Id(Long artistaId);

    List<Postulacion> findByAceptada(boolean aceptada);


    // SQL Nativo
    @Query(value = "SELECT * FROM postulaciones WHERE id_anuncios = ?1", nativeQuery = true)
    List<Postulacion> findByAnuncioSQL(Long anuncioId);


    // JPQL
    @Query("SELECT p FROM Postulacion p WHERE p.anuncio.id = ?1")
    List<Postulacion> findByAnuncioJPQL(Long anuncioId);
}
