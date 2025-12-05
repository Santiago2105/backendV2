package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.EventoDTO;
import java.time.LocalDate;
import java.util.List;

public interface EventoService {

    // CRUD
    EventoDTO add(EventoDTO eventoDTO);

    EventoDTO update(Long id, EventoDTO eventoDTO);

    void delete(Long id);

    EventoDTO findById(Long id);

    List<EventoDTO> listAll();

    // Query Methods
    List<EventoDTO> findByRestauranteId(Long restauranteId);

    List<EventoDTO> findByFechaEvento(LocalDate fechaEvento);

    // SQL Nativo
    List<EventoDTO> findByRestauranteSQL(Long restauranteId);

    // JPQL
    List<EventoDTO> findByRestauranteJPQL(Long restauranteId);
}
