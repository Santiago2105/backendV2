package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Anuncio;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.AnuncioRepository;
import pe.edu.upc.backend.services.AnuncioService;

import java.util.List;

@Service
public class AnuncioServiceImpl implements AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public Anuncio add(Anuncio anuncio) {

        // Validación de campos requeridos
        if (anuncio.getTitulo() == null || anuncio.getTitulo().isBlank()) {
            throw new RequiredDataException("El título del anuncio no puede ser nulo o vacío.");
        }
        if (anuncio.getDescripcion() == null || anuncio.getDescripcion().isBlank()) {
            throw new RequiredDataException("La descripción no puede ser nula o vacía.");
        }
        if (anuncio.getEvento() == null) {
            throw new RequiredDataException("El anuncio debe estar asociado a un evento.");
        }

        return anuncioRepository.save(anuncio);
    }

    @Override
    public void delete(Long id) {
        Anuncio anuncioFound = findById(id);

        if (anuncioFound == null) {
            throw new ResourceNotFoundException("No se encontró el anuncio con id: " + id);
        }

        anuncioRepository.deleteById(id);
    }

    @Override
    public Anuncio findById(Long id) {
        return anuncioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Anuncio> listAll() {
        return anuncioRepository.findAll();
    }

    @Override
    public Anuncio edit(Anuncio anuncio) {

        Anuncio anuncioFound = findById(anuncio.getId());
        if (anuncioFound == null) {
            return null;
        }

        // Actualización de atributos
        if (anuncio.getTitulo() != null && !anuncio.getTitulo().isBlank()) {
            anuncioFound.setTitulo(anuncio.getTitulo());
        }

        if (anuncio.getDescripcion() != null && !anuncio.getDescripcion().isBlank()) {
            anuncioFound.setDescripcion(anuncio.getDescripcion());
        }

        if (anuncio.getGeneroBuscado() != null && !anuncio.getGeneroBuscado().isBlank()) {
            anuncioFound.setGeneroBuscado(anuncio.getGeneroBuscado());
        }

        if (anuncio.getFechaEvento() != null) {
            anuncioFound.setFechaEvento(anuncio.getFechaEvento());
        }

        if (anuncio.getUbicacion() != null && !anuncio.getUbicacion().isBlank()) {
            anuncioFound.setUbicacion(anuncio.getUbicacion());
        }

        if (anuncio.getPresupuesto() != null && !anuncio.getPresupuesto().isBlank()) {
            anuncioFound.setPresupuesto(anuncio.getPresupuesto());
        }

        anuncioFound.setActivo(anuncio.isActivo());

        if (anuncio.getFechaCreacion() != null) {
            anuncioFound.setFechaCreacion(anuncio.getFechaCreacion());
        }

        if (anuncio.getEvento() != null) {
            anuncioFound.setEvento(anuncio.getEvento());
        }

        return anuncioRepository.save(anuncioFound);
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<Anuncio> findByEventoId(Long eventoId) {
        return anuncioRepository.findByEvento_Id(eventoId);
    }

    @Override
    public List<Anuncio> findByActivo(boolean activo) {
        return anuncioRepository.findByActivo(activo);
    }

    @Override
    public List<Anuncio> findByGeneroBuscado(String genero) {
        return anuncioRepository.findByGeneroBuscado(genero);
    }

    // ----------------------------------------------------
    // SQL Nativo
    // ----------------------------------------------------

    @Override
    public List<Anuncio> findByEventoSQL(Long eventoId) {
        return anuncioRepository.findByEventoSQL(eventoId);
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<Anuncio> findByEventoJPQL(Long eventoId) {
        return anuncioRepository.findByEventoJPQL(eventoId);
    }
}
