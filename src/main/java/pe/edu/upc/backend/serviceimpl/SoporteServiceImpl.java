package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.SoporteDTO;
import pe.edu.upc.backend.entities.Soporte;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.SoporteRepository;
import pe.edu.upc.backend.repositories.UserRepository;
import pe.edu.upc.backend.services.SoporteService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoporteServiceImpl implements SoporteService {

    @Autowired
    private SoporteRepository soporteRepository;
    @Autowired
    private UserRepository userRepository;

    // --- Mappers ---
    private SoporteDTO toDTO(Soporte entity) {
        if (entity == null) return null;
        SoporteDTO dto = new SoporteDTO();
        dto.setId(entity.getId());
        dto.setAsunto(entity.getAsunto());
        dto.setDescripcion(entity.getDescripcion());
        dto.setEstado(entity.isEstado());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaCierre(entity.getFechaCierre());

        if (entity.getUsuario() != null) dto.setUsuarioId(entity.getUsuario().getId());
        return dto;
    }

    private Soporte toEntity(SoporteDTO dto) {
        Soporte entity = new Soporte();
        entity.setAsunto(dto.getAsunto());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEstado(dto.isEstado());

        if(dto.getFechaCreacion() != null) entity.setFechaCreacion(dto.getFechaCreacion());
        else entity.setFechaCreacion(LocalDate.now());

        entity.setFechaCierre(dto.getFechaCierre());

        if (dto.getUsuarioId() != null) {
            User user = userRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            entity.setUsuario(user);
        }
        return entity;
    }

    private List<SoporteDTO> toDTOList(List<Soporte> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // --- CRUD ---
    @Override
    public SoporteDTO add(SoporteDTO dto) {
        if (dto.getAsunto() == null || dto.getAsunto().isBlank()) throw new RequiredDataException("Asunto requerido.");
        if (dto.getUsuarioId() == null) throw new RequiredDataException("Usuario requerido.");

        Soporte entity = toEntity(dto);
        entity.setEstado(true); // Abierto por defecto
        return toDTO(soporteRepository.save(entity));
    }

    @Override
    public SoporteDTO update(Long id, SoporteDTO dto) {
        Soporte entity = soporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        if(dto.getAsunto() != null) entity.setAsunto(dto.getAsunto());
        if(dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());

        // Cambiar estado (Cerrar ticket)
        entity.setEstado(dto.isEstado());
        if (!dto.isEstado()) { // Si se cierra (false)
            entity.setFechaCierre(LocalDate.now());
        }

        return toDTO(soporteRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if(!soporteRepository.existsById(id)) throw new ResourceNotFoundException("No encontrado");
        soporteRepository.deleteById(id);
    }

    @Override
    public SoporteDTO findById(Long id) {
        return toDTO(soporteRepository.findById(id).orElse(null));
    }

    @Override
    public List<SoporteDTO> listAll() {
        return toDTOList(soporteRepository.findAll());
    }

    // --- Queries ---
    @Override
    public List<SoporteDTO> findByEstado(boolean estado) {
        return toDTOList(soporteRepository.findByEstado(estado));
    }

    @Override
    public List<SoporteDTO> findByUsuarioId(Long usuarioId) {
        return toDTOList(soporteRepository.findByUsuario_Id(usuarioId));
    }

    @Override
    public List<SoporteDTO> findByEstadoSQL(boolean estado) {
        return toDTOList(soporteRepository.findByEstadoSQL(estado));
    }

    @Override
    public List<SoporteDTO> findByEstadoJPQL(boolean estado) {
        return toDTOList(soporteRepository.findByEstadoJPQL(estado));
    }
}