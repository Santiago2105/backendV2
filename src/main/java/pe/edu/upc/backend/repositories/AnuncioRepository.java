package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backend.entities.Anuncio;

import java.util.List;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

    // Query Method
    List<Anuncio> findByEvento_Id(Long eventoId);

    List<Anuncio> findByActivo(boolean activo);

    List<Anuncio> findByGeneroBuscado(String genero);


    @Query(value = "SELECT * FROM anuncios WHERE id_eventos = :id", nativeQuery = true)
    List<Anuncio> findByEventoSQL(@Param("id") Long eventoId);

    @Query("SELECT a FROM Anuncio a WHERE a.evento.id = :id")
    List<Anuncio> findByEventoJPQL(@Param("id") Long eventoId);
}

