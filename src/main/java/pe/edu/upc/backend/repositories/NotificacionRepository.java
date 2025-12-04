package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Notificacion;

import java.time.LocalDate;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Query Method
    List<Notificacion> findByUsuario_Id(Long usuarioId);

    List<Notificacion> findByLeido(boolean leido);

    List<Notificacion> findByFechaNotificacionBetween(LocalDate inicio, LocalDate fin);


    // SQL Nativo
    @Query(value = "SELECT * FROM notificaciones WHERE id_usuarios = ?1", nativeQuery = true)
    List<Notificacion> findByUsuarioSQL(Long usuarioId);


    // JPQL
    @Query("SELECT n FROM Notificacion n WHERE n.usuario.id = ?1")
    List<Notificacion> findByUsuarioJPQL(Long usuarioId);
}
