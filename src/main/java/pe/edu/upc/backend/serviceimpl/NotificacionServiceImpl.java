package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.NotificacionDTO;
import pe.edu.upc.backend.entities.Notificacion;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.NotificacionRepository;
import pe.edu.upc.backend.repositories.UserRepository;
import pe.edu.upc.backend.services.NotificacionService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;
    @Autowired
    private UserRepository userRepository;

    // --- Mappers ---
    private NotificacionDTO toDTO(Notificacion entity) {
        if(entity == null) return null;
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(entity.getId());
        dto.setMensaje(entity.getMensaje());
        dto.setTipoNotificacion(entity.getTipoNotificacion());
        dto.setLeido(entity.isLeido());
        dto.setFechaNotificacion(entity.getFechaNotificacion());

        if(entity.getUsuario() != null) dto.setUsuarioId(entity.getUsuario().getId());
        return dto;
    }

    private Notificacion toEntity(NotificacionDTO dto) {
        Notificacion entity = new Notificacion();
        entity.setMensaje(dto.getMensaje());
        entity.setTipoNotificacion(dto.getTipoNotificacion());
        entity.setLeido(dto.isLeido());

        if(dto.getFechaNotificacion() != null) entity.setFechaNotificacion(dto.getFechaNotificacion());
        else entity.setFechaNotificacion(LocalDate.now());

        if(dto.getUsuarioId() != null) {
            User user = userRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            entity.setUsuario(user);
        }
        return entity;
    }

    private List<NotificacionDTO> toDTOList(List<Notificacion> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // --- CRUD ---
    @Override
    public NotificacionDTO add(NotificacionDTO dto) {
        if (dto.getMensaje() == null || dto.getMensaje().isBlank()) throw new RequiredDataException("Mensaje requerido.");
        if (dto.getUsuarioId() == null) throw new RequiredDataException("Usuario destino requerido.");

        Notificacion entity = toEntity(dto);
        // Al crear, por defecto no está leída
        entity.setLeido(false);
        return toDTO(notificacionRepository.save(entity));
    }

    @Override
    public NotificacionDTO update(Long id, NotificacionDTO dto) {
        Notificacion entity = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));

        if(dto.getMensaje() != null) entity.setMensaje(dto.getMensaje());
        if(dto.getTipoNotificacion() != null) entity.setTipoNotificacion(dto.getTipoNotificacion());

        // Actualizar estado leído
        entity.setLeido(dto.isLeido());

        return toDTO(notificacionRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if(!notificacionRepository.existsById(id)) throw new ResourceNotFoundException("No encontrado");
        notificacionRepository.deleteById(id);
    }

    @Override
    public NotificacionDTO findById(Long id) {
        return toDTO(notificacionRepository.findById(id).orElse(null));
    }

    @Override
    public List<NotificacionDTO> listAll() {
        return toDTOList(notificacionRepository.findAll());
    }

    // --- Queries ---
    @Override
    public List<NotificacionDTO> findByUsuarioId(Long usuarioId) {
        return toDTOList(notificacionRepository.findByUsuario_Id(usuarioId));
    }

    @Override
    public List<NotificacionDTO> findByLeido(boolean leido) {
        return toDTOList(notificacionRepository.findByLeido(leido));
    }

    @Override
    public List<NotificacionDTO> findByFechaNotificacionBetween(LocalDate inicio, LocalDate fin) {
        return toDTOList(notificacionRepository.findByFechaNotificacionBetween(inicio, fin));
    }

    @Override
    public List<NotificacionDTO> findByUsuarioSQL(Long usuarioId) {
        return toDTOList(notificacionRepository.findByUsuarioSQL(usuarioId));
    }

    @Override
    public List<NotificacionDTO> findByUsuarioJPQL(Long usuarioId) {
        return toDTOList(notificacionRepository.findByUsuarioJPQL(usuarioId));
    }
}