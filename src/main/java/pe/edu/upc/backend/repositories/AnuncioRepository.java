package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Anuncio;

import java.util.List;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

    // Query Method
    List<Anuncio> findByEvento_Id(Long eventoId);

    List<Anuncio> findByActivo(boolean activo);

    List<Anuncio> findByGeneroBuscado(String genero);


    // SQL Nativo
    @Query(value = "SELECT * FROM anuncios WHERE id_eventos = ?1", nativeQuery = true)
    List<Anuncio> findByEventoSQL(Long eventoId);


    // JPQL
    @Query("SELECT a FROM Anuncio a WHERE a.evento.id = ?1")
    List<Anuncio> findByEventoJPQL(Long eventoId);
}

