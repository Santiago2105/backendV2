package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backend.entities.Soporte;

import java.util.List;

public interface SoporteRepository extends JpaRepository<Soporte, Long> {

    // Query Method
    List<Soporte> findByEstado(boolean estado);

    List<Soporte> findByUsuario_Id(Long usuarioId);


    @Query(value = "SELECT * FROM soporte WHERE estado = :est", nativeQuery = true)
    List<Soporte> findByEstadoSQL(@Param("est") boolean estado);

    @Query("SELECT s FROM Soporte s WHERE s.estado = :est")
    List<Soporte> findByEstadoJPQL(@Param("est") boolean estado);
}
