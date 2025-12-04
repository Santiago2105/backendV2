package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Notificacion;

import java.time.LocalDate;
import java.util.List;

public interface NotificacionService {

    // CRUD
    Notificacion add(Notificacion notificacion);

    void delete(Long id);

    Notificacion findById(Long id);

    List<Notificacion> listAll();

    Notificacion edit(Notificacion notificacion);


    // Query Methods (del repositorio)
    List<Notificacion> findByUsuarioId(Long usuarioId);

    List<Notificacion> findByLeido(boolean leido);

    List<Notificacion> findByFechaNotificacionBetween(LocalDate inicio, LocalDate fin);


    // SQL Nativo
    List<Notificacion> findByUsuarioSQL(Long usuarioId);


    // JPQL
    List<Notificacion> findByUsuarioJPQL(Long usuarioId);
}
