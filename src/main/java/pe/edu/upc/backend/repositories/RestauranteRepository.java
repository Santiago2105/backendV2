package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Restaurante;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    // Query Method
    List<Restaurante> findByCiudad(String ciudad);

    List<Restaurante> findByAforoMesasGreaterThan(Integer aforo);

    List<Restaurante> findByUsuario_Id(Long usuarioId);


    // SQL Nativo
    @Query(value = "SELECT * FROM restaurantes WHERE ciudad = ?1", nativeQuery = true)
    List<Restaurante> findByCiudadSQL(String ciudad);


    // JPQL
    @Query("SELECT r FROM Restaurante r WHERE r.ciudad = ?1")
    List<Restaurante> findByCiudadJPQL(String ciudad);
}

