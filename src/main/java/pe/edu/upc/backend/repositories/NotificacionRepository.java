package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backend.entities.Notificacion;

import java.time.LocalDate;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Query Method
    List<Notificacion> findByUsuario_Id(Long usuarioId);

    List<Notificacion> findByLeido(boolean leido);

    List<Notificacion> findByFechaNotificacionBetween(LocalDate inicio, LocalDate fin);


    @Query(value = "SELECT * FROM notificaciones WHERE id_usuarios = :id", nativeQuery = true)
    List<Notificacion> findByUsuarioSQL(@Param("id") Long usuarioId);

    @Query("SELECT n FROM Notificacion n WHERE n.usuario.id = :id")
    List<Notificacion> findByUsuarioJPQL(@Param("id") Long usuarioId);
}
