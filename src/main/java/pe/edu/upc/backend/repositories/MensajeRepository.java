package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Mensaje;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    // Query Method
    List<Mensaje> findByAnuncio_Id(Long anuncioId);

    List<Mensaje> findByUsuario_Id(Long usuarioId);


    // SQL Nativo
    @Query(value = "SELECT * FROM mensajes WHERE id_anuncios = ?1", nativeQuery = true)
    List<Mensaje> findByAnuncioSQL(Long anuncioId);


    // JPQL
    @Query("SELECT m FROM Mensaje m WHERE m.anuncio.id = ?1")
    List<Mensaje> findByAnuncioJPQL(Long anuncioId);
}
