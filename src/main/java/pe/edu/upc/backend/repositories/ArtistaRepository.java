package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Artista;

import java.util.List;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    // Query Method
    List<Artista> findByGeneroPrincipal(String genero);

    List<Artista> findByCiudad(String ciudad);

    List<Artista> findByUsuario_Id(Long usuarioId);


    // SQL Nativo
    @Query(value = "SELECT * FROM artistas WHERE ciudad = ?1", nativeQuery = true)
    List<Artista> findByCiudadSQL(String ciudad);


    // JPQL
    @Query("SELECT a FROM Artista a WHERE a.ciudad = ?1")
    List<Artista> findByCiudadJPQL(String ciudad);
}
