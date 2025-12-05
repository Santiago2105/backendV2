package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.EventoDTO;
import pe.edu.upc.backend.entities.Evento;
import pe.edu.upc.backend.entities.Restaurante;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.EventoRepository;
import pe.edu.upc.backend.repositories.RestauranteRepository;
import pe.edu.upc.backend.services.EventoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    // ----------------------------------------------------
    // Mappers
    // ----------------------------------------------------

    private EventoDTO toDTO(Evento evento) {
        if (evento == null) return null;

        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setFechaEvento(evento.getFechaEvento());
        dto.setCachet(evento.getCachet());
        dto.setRealizado(evento.isRealizado());
        dto.setFechaCreacion(evento.getFechaCreacion());

        if (evento.getRestaurante() != null) {
            dto.setRestauranteId(evento.getRestaurante().getId());
        }

        return dto;
    }

    private Evento toEntity(EventoDTO dto) {
        Evento evento = new Evento();

        evento.setFechaEvento(dto.getFechaEvento());
        evento.setCachet(dto.getCachet());
        evento.setRealizado(dto.isRealizado());

        // Fecha de creación: si viene en el DTO la respetamos, si no usamos hoy
        if (dto.getFechaCreacion() != null) {
            evento.setFechaCreacion(dto.getFechaCreacion());
        } else {
            evento.setFechaCreacion(LocalDate.now());
        }

        // Relación con restaurante (obligatoria)
        if (dto.getRestauranteId() != null) {
            Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Restaurante no encontrado id: " + dto.getRestauranteId()));
            evento.setRestaurante(restaurante);
        }

        return evento;
    }

    private List<EventoDTO> toDTOList(List<Evento> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public EventoDTO add(EventoDTO dto) {
        if (dto.getFechaEvento() == null) {
            throw new RequiredDataException("La fecha del evento es obligatoria.");
        }
        if (dto.getRestauranteId() == null) {
            throw new RequiredDataException("El evento debe tener un restaurante asignado.");
        }

        Evento evento = toEntity(dto);
        // Forzamos la fecha de creación al momento actual
        evento.setFechaCreacion(LocalDate.now());

        return toDTO(eventoRepository.save(evento));
    }

    @Override
    public EventoDTO update(Long id, EventoDTO dto) {
        Evento eventoFound = eventoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Evento no encontrado con id: " + id));

        // Actualizar campos simples
        eventoFound.setFechaEvento(dto.getFechaEvento());
        eventoFound.setCachet(dto.getCachet());
        eventoFound.setRealizado(dto.isRealizado());

        // Actualizar restaurante si viene en el DTO
        if (dto.getRestauranteId() != null) {
            Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Restaurante no encontrado id: " + dto.getRestauranteId()));
            eventoFound.setRestaurante(restaurante);
        }

        return toDTO(eventoRepository.save(eventoFound));
    }

    @Override
    public void delete(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evento no encontrado con id: " + id);
        }
        eventoRepository.deleteById(id);
    }

    @Override
    public EventoDTO findById(Long id) {
        return toDTO(eventoRepository.findById(id).orElse(null));
    }

    @Override
    public List<EventoDTO> listAll() {
        return toDTOList(eventoRepository.findAll());
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<EventoDTO> findByRestauranteId(Long restauranteId) {
        return toDTOList(eventoRepository.findByRestaurante_Id(restauranteId));
    }

    @Override
    public List<EventoDTO> findByFechaEvento(LocalDate fechaEvento) {
        return toDTOList(eventoRepository.findByFechaEvento(fechaEvento));
    }

    @Override
    public List<EventoDTO> findByRestauranteSQL(Long restauranteId) {
        return toDTOList(eventoRepository.findByRestauranteSQL(restauranteId));
    }

    @Override
    public List<EventoDTO> findByRestauranteJPQL(Long restauranteId) {
        return toDTOList(eventoRepository.findByRestauranteJPQL(restauranteId));
    }
}
