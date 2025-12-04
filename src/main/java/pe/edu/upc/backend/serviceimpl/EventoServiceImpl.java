package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Evento;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.EventoRepository;
import pe.edu.upc.backend.services.EventoService;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public Evento add(Evento evento) {

        // Validación de campos obligatorios
        if (evento.getFechaEvento() == null) {
            throw new RequiredDataException("La fecha del evento no puede ser nula.");
        }
        if (evento.getRestaurante() == null) {
            throw new RequiredDataException("El evento debe estar asociado a un restaurante.");
        }
        if (evento.getArtista() == null) {
            throw new RequiredDataException("El evento debe estar asociado a un artista.");
        }

        // Registro del evento
        return eventoRepository.save(evento);
    }

    @Override
    public void delete(Long id) {
        Evento eventoFound = findById(id);

        if (eventoFound == null) {
            throw new ResourceNotFoundException("No se encontró el evento con id: " + id);
        }

        eventoRepository.deleteById(id);
    }

    @Override
    public Evento findById(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Evento> listAll() {
        return eventoRepository.findAll();
    }

    @Override
    public Evento edit(Evento evento) {

        Evento eventoFound = findById(evento.getId());
        if (eventoFound == null) {
            return null;
        }

        // Actualización de valores enviados
        if (evento.getFechaEvento() != null) {
            eventoFound.setFechaEvento(evento.getFechaEvento());
        }

        if (evento.getCachet() != null && !evento.getCachet().isBlank()) {
            eventoFound.setCachet(evento.getCachet());
        }

        eventoFound.setRealizado(evento.isRealizado());

        if (evento.getFechaCreacion() != null) {
            eventoFound.setFechaCreacion(evento.getFechaCreacion());
        }

        if (evento.getRestaurante() != null) {
            eventoFound.setRestaurante(evento.getRestaurante());
        }

        if (evento.getArtista() != null) {
            eventoFound.setArtista(evento.getArtista());
        }

        return eventoRepository.save(eventoFound);
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<Evento> findByRestauranteId(Long restauranteId) {
        return eventoRepository.findByRestaurante_Id(restauranteId);
    }

    @Override
    public List<Evento> findByArtistaId(Long artistaId) {
        return eventoRepository.findByArtista_Id(artistaId);
    }

    @Override
    public List<Evento> findByFechaEvento(LocalDate fechaEvento) {
        return eventoRepository.findByFechaEvento(fechaEvento);
    }

    // ----------------------------------------------------
    // SQL Nativo
    // ----------------------------------------------------

    @Override
    public List<Evento> findByRestauranteSQL(Long restauranteId) {
        return eventoRepository.findByRestauranteSQL(restauranteId);
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<Evento> findByRestauranteJPQL(Long restauranteId) {
        return eventoRepository.findByRestauranteJPQL(restauranteId);
    }
}
