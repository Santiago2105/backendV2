package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Notificacion;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.NotificacionRepository;
import pe.edu.upc.backend.services.NotificacionService;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public Notificacion add(Notificacion notificacion) {

        // Validación de campos requeridos
        if (notificacion.getMensaje() == null || notificacion.getMensaje().isBlank()) {
            throw new RequiredDataException("El mensaje de la notificación no puede ser nulo o vacío.");
        }
        if (notificacion.getTipoNotificacion() == null || notificacion.getTipoNotificacion().isBlank()) {
            throw new RequiredDataException("El tipo de notificación no puede ser nulo o vacío.");
        }
        if (notificacion.getUsuario() == null) {
            throw new RequiredDataException("La notificación debe estar asociada a un usuario.");
        }

        // Asignar fecha si no viene definida
        if (notificacion.getFechaNotificacion() == null) {
            notificacion.setFechaNotificacion(LocalDate.now());
        }

        return notificacionRepository.save(notificacion);
    }

    @Override
    public void delete(Long id) {
        Notificacion notificacionFound = findById(id);

        if (notificacionFound == null) {
            throw new ResourceNotFoundException("No se encontró la notificación con id: " + id);
        }

        notificacionRepository.deleteById(id);
    }

    @Override
    public Notificacion findById(Long id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Notificacion> listAll() {
        return notificacionRepository.findAll();
    }

    @Override
    public Notificacion edit(Notificacion notificacion) {

        Notificacion notificacionFound = findById(notificacion.getId());
        if (notificacionFound == null) {
            return null;
        }

        // Actualizar datos válidos
        if (notificacion.getMensaje() != null && !notificacion.getMensaje().isBlank()) {
            notificacionFound.setMensaje(notificacion.getMensaje());
        }

        if (notificacion.getTipoNotificacion() != null && !notificacion.getTipoNotificacion().isBlank()) {
            notificacionFound.setTipoNotificacion(notificacion.getTipoNotificacion());
        }

        notificacionFound.setLeido(notificacion.isLeido());

        if (notificacion.getFechaNotificacion() != null) {
            notificacionFound.setFechaNotificacion(notificacion.getFechaNotificacion());
        }

        if (notificacion.getUsuario() != null) {
            notificacionFound.setUsuario(notificacion.getUsuario());
        }

        return notificacionRepository.save(notificacionFound);
    }

    // ----------------------------------------------------
    // QUERY METHODS
    // ----------------------------------------------------

    @Override
    public List<Notificacion> findByUsuarioId(Long usuarioId) {
        return notificacionRepository.findByUsuario_Id(usuarioId);
    }

    @Override
    public List<Notificacion> findByLeido(boolean leido) {
        return notificacionRepository.findByLeido(leido);
    }

    @Override
    public List<Notificacion> findByFechaNotificacionBetween(LocalDate inicio, LocalDate fin) {
        return notificacionRepository.findByFechaNotificacionBetween(inicio, fin);
    }

    // ----------------------------------------------------
    // SQL NATIVO
    // ----------------------------------------------------

    @Override
    public List<Notificacion> findByUsuarioSQL(Long usuarioId) {
        return notificacionRepository.findByUsuarioSQL(usuarioId);
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<Notificacion> findByUsuarioJPQL(Long usuarioId) {
        return notificacionRepository.findByUsuarioJPQL(usuarioId);
    }
}
