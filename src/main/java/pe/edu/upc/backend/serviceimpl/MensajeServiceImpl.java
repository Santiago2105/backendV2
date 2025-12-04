package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.MensajeDTO;
import pe.edu.upc.backend.entities.Anuncio;
import pe.edu.upc.backend.entities.Mensaje;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.AnuncioRepository;
import pe.edu.upc.backend.repositories.MensajeRepository;
import pe.edu.upc.backend.repositories.UserRepository;
import pe.edu.upc.backend.services.MensajeService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;
    @Autowired
    private AnuncioRepository anuncioRepository;
    @Autowired
    private UserRepository userRepository;

    // --- Mappers ---
    private MensajeDTO toDTO(Mensaje mensaje) {
        if (mensaje == null) return null;
        MensajeDTO dto = new MensajeDTO();
        dto.setId(mensaje.getId());
        dto.setTexto(mensaje.getTexto());
        dto.setFechaEnvio(mensaje.getFechaEnvio());

        if (mensaje.getAnuncio() != null) dto.setAnuncioId(mensaje.getAnuncio().getId());
        if (mensaje.getUsuario() != null) dto.setUsuarioId(mensaje.getUsuario().getId());

        return dto;
    }

    private Mensaje toEntity(MensajeDTO dto) {
        Mensaje mensaje = new Mensaje();
        mensaje.setTexto(dto.getTexto());

        if(dto.getFechaEnvio() != null) mensaje.setFechaEnvio(dto.getFechaEnvio());
        else mensaje.setFechaEnvio(LocalDate.now());

        if (dto.getAnuncioId() != null) {
            Anuncio anuncio = anuncioRepository.findById(dto.getAnuncioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Anuncio no encontrado id: " + dto.getAnuncioId()));
            mensaje.setAnuncio(anuncio);
        }
        if (dto.getUsuarioId() != null) {
            User user = userRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado id: " + dto.getUsuarioId()));
            mensaje.setUsuario(user);
        }
        return mensaje;
    }

    private List<MensajeDTO> toDTOList(List<Mensaje> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // --- CRUD ---
    @Override
    public MensajeDTO add(MensajeDTO dto) {
        if (dto.getTexto() == null || dto.getTexto().isBlank()) {
            throw new RequiredDataException("El texto es obligatorio.");
        }
        if (dto.getAnuncioId() == null || dto.getUsuarioId() == null) {
            throw new RequiredDataException("El mensaje debe tener anuncio y usuario.");
        }

        Mensaje mensaje = toEntity(dto);
        return toDTO(mensajeRepository.save(mensaje));
    }

    @Override
    public MensajeDTO update(Long id, MensajeDTO dto) {
        Mensaje msg = mensajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje no encontrado id: " + id));

        msg.setTexto(dto.getTexto());
        if(dto.getFechaEnvio() != null) msg.setFechaEnvio(dto.getFechaEnvio());

        // No solemos cambiar el usuario o anuncio de un mensaje ya enviado,
        // pero si lo necesitas, añade la lógica aquí similar a add()

        return toDTO(mensajeRepository.save(msg));
    }

    @Override
    public void delete(Long id) {
        if(!mensajeRepository.existsById(id)) throw new ResourceNotFoundException("Mensaje no encontrado id: " + id);
        mensajeRepository.deleteById(id);
    }

    @Override
    public MensajeDTO findById(Long id) {
        return toDTO(mensajeRepository.findById(id).orElse(null));
    }

    @Override
    public List<MensajeDTO> listAll() {
        return toDTOList(mensajeRepository.findAll());
    }

    // --- Queries ---
    @Override
    public List<MensajeDTO> findByAnuncioId(Long anuncioId) {
        return toDTOList(mensajeRepository.findByAnuncio_Id(anuncioId));
    }

    @Override
    public List<MensajeDTO> findByUsuarioId(Long usuarioId) {
        return toDTOList(mensajeRepository.findByUsuario_Id(usuarioId));
    }

    @Override
    public List<MensajeDTO> findByAnuncioSQL(Long anuncioId) {
        return toDTOList(mensajeRepository.findByAnuncioSQL(anuncioId));
    }

    @Override
    public List<MensajeDTO> findByAnuncioJPQL(Long anuncioId) {
        return toDTOList(mensajeRepository.findByAnuncioJPQL(anuncioId));
    }
}