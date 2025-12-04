package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.AnuncioDTO;
import pe.edu.upc.backend.entities.Anuncio;
import pe.edu.upc.backend.entities.Evento;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.AnuncioRepository;
import pe.edu.upc.backend.repositories.EventoRepository;
import pe.edu.upc.backend.services.AnuncioService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnuncioServiceImpl implements AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;
    @Autowired
    private EventoRepository eventoRepository;

    // ----------------------------------------------------
    // Mappers
    // ----------------------------------------------------

    private AnuncioDTO toDTO(Anuncio anuncio) {
        if (anuncio == null) return null;
        AnuncioDTO dto = new AnuncioDTO();
        dto.setId(anuncio.getId());
        dto.setTitulo(anuncio.getTitulo());
        dto.setDescripcion(anuncio.getDescripcion());
        dto.setGeneroBuscado(anuncio.getGeneroBuscado());
        dto.setFechaEvento(anuncio.getFechaEvento());
        dto.setUbicacion(anuncio.getUbicacion());
        dto.setPresupuesto(anuncio.getPresupuesto());
        dto.setActivo(anuncio.isActivo());
        dto.setFechaCreacion(anuncio.getFechaCreacion());

        if (anuncio.getEvento() != null) {
            dto.setEventoId(anuncio.getEvento().getId());
        }
        return dto;
    }

    private Anuncio toEntity(AnuncioDTO dto) {
        Anuncio anuncio = new Anuncio();
        anuncio.setTitulo(dto.getTitulo());
        anuncio.setDescripcion(dto.getDescripcion());
        anuncio.setGeneroBuscado(dto.getGeneroBuscado());
        anuncio.setFechaEvento(dto.getFechaEvento());
        anuncio.setUbicacion(dto.getUbicacion());
        anuncio.setPresupuesto(dto.getPresupuesto());
        anuncio.setActivo(dto.isActivo());

        if(dto.getFechaCreacion() != null) {
            anuncio.setFechaCreacion(dto.getFechaCreacion());
        } else {
            anuncio.setFechaCreacion(LocalDate.now());
        }

        if (dto.getEventoId() != null) {
            Evento evento = eventoRepository.findById(dto.getEventoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado id: " + dto.getEventoId()));
            anuncio.setEvento(evento);
        }
        return anuncio;
    }

    private List<AnuncioDTO> toDTOList(List<Anuncio> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public AnuncioDTO add(AnuncioDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new RequiredDataException("El título es obligatorio.");
        }
        if (dto.getEventoId() == null) {
            throw new RequiredDataException("El anuncio debe estar asociado a un evento.");
        }

        Anuncio anuncio = toEntity(dto);
        // Si no se envió fecha de evento en el anuncio, podríamos tomarla del evento padre (opcional)
        // Pero tu modelo tiene el campo, así que lo respetamos.
        return toDTO(anuncioRepository.save(anuncio));
    }

    @Override
    public AnuncioDTO update(Long id, AnuncioDTO dto) {
        Anuncio anuncioFound = anuncioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anuncio no encontrado con id: " + id));

        anuncioFound.setTitulo(dto.getTitulo());
        anuncioFound.setDescripcion(dto.getDescripcion());
        anuncioFound.setGeneroBuscado(dto.getGeneroBuscado());
        anuncioFound.setUbicacion(dto.getUbicacion());
        anuncioFound.setPresupuesto(dto.getPresupuesto());
        anuncioFound.setActivo(dto.isActivo());
        if(dto.getFechaEvento() != null) anuncioFound.setFechaEvento(dto.getFechaEvento());

        if (dto.getEventoId() != null) {
            Evento evento = eventoRepository.findById(dto.getEventoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado id: " + dto.getEventoId()));
            anuncioFound.setEvento(evento);
        }

        return toDTO(anuncioRepository.save(anuncioFound));
    }

    @Override
    public void delete(Long id) {
        if (!anuncioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Anuncio no encontrado con id: " + id);
        }
        anuncioRepository.deleteById(id);
    }

    @Override
    public AnuncioDTO findById(Long id) {
        return toDTO(anuncioRepository.findById(id).orElse(null));
    }

    @Override
    public List<AnuncioDTO> listAll() {
        return toDTOList(anuncioRepository.findAll());
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<AnuncioDTO> findByEventoId(Long eventoId) {
        return toDTOList(anuncioRepository.findByEvento_Id(eventoId));
    }

    @Override
    public List<AnuncioDTO> findByActivo(boolean activo) {
        return toDTOList(anuncioRepository.findByActivo(activo));
    }

    @Override
    public List<AnuncioDTO> findByGeneroBuscado(String genero) {
        return toDTOList(anuncioRepository.findByGeneroBuscado(genero));
    }

    @Override
    public List<AnuncioDTO> findByEventoSQL(Long eventoId) {
        return toDTOList(anuncioRepository.findByEventoSQL(eventoId));
    }

    @Override
    public List<AnuncioDTO> findByEventoJPQL(Long eventoId) {
        return toDTOList(anuncioRepository.findByEventoJPQL(eventoId));
    }
}