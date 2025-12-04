package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Soporte;

import java.util.List;

public interface SoporteRepository extends JpaRepository<Soporte, Long> {

    // Query Method
    List<Soporte> findByEstado(boolean estado);

    List<Soporte> findByUsuario_Id(Long usuarioId);


    // SQL Nativo
    @Query(value = "SELECT * FROM soporte WHERE estado = ?1", nativeQuery = true)
    List<Soporte> findByEstadoSQL(boolean estado);


    // JPQL
    @Query("SELECT s FROM Soporte s WHERE s.estado = ?1")
    List<Soporte> findByEstadoJPQL(boolean estado);
}
