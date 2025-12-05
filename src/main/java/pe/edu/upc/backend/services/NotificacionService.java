package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.NotificacionDTO;
import java.time.LocalDate;
import java.util.List;

public interface NotificacionService {

    NotificacionDTO add(NotificacionDTO notificacionDTO);

    NotificacionDTO update(Long id, NotificacionDTO notificacionDTO);

    void delete(Long id);

    NotificacionDTO findById(Long id);

    List<NotificacionDTO> listAll();

    List<NotificacionDTO> findByUsuarioId(Long usuarioId);

    List<NotificacionDTO> findByLeido(boolean leido);

    List<NotificacionDTO> findByFechaNotificacionBetween(LocalDate inicio, LocalDate fin);

    List<NotificacionDTO> findByUsuarioSQL(Long usuarioId);

    List<NotificacionDTO> findByUsuarioJPQL(Long usuarioId);
}