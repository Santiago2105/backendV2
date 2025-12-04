package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Resenia;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.ReseniaRepository;
import pe.edu.upc.backend.services.ReseniaService;

import java.util.List;

@Service
public class ReseniaServiceImpl implements ReseniaService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public Resenia add(Resenia resenia) {

        // Validación de campos obligatorios
        if (resenia.getPuntuacion() == null ||
                (!resenia.getPuntuacion().equals("A")
                        && !resenia.getPuntuacion().equals("B")
                        && !resenia.getPuntuacion().equals("C")
                        && !resenia.getPuntuacion().equals("D")
                        && !resenia.getPuntuacion().equals("E"))) {
            throw new RequiredDataException("La puntuación debe ser un valor válido (A–E).");
        }

        if (resenia.getEvento() == null) {
            throw new RequiredDataException("La reseña debe estar asociada a un evento.");
        }

        if (resenia.getArtista() == null) {
            throw new RequiredDataException("La reseña debe estar asociada a un artista.");
        }

        if (resenia.getRestaurante() == null) {
            throw new RequiredDataException("La reseña debe estar asociada a un restaurante.");
        }

        return reseniaRepository.save(resenia);
    }

    @Override
    public void delete(Long id) {
        Resenia reseniaFound = findById(id);

        if (reseniaFound == null) {
            throw new ResourceNotFoundException("No se encontró la reseña con id: " + id);
        }

        reseniaRepository.deleteById(id);
    }

    @Override
    public Resenia findById(Long id) {
        return reseniaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Resenia> listAll() {
        return reseniaRepository.findAll();
    }

    @Override
    public Resenia edit(Resenia resenia) {

        Resenia reseniaFound = findById(resenia.getId());
        if (reseniaFound == null) {
            return null;
        }

        // Actualización de atributos
        if (resenia.getPuntuacion() != null
                && !resenia.getPuntuacion().isBlank()) {
            reseniaFound.setPuntuacion(resenia.getPuntuacion());
        }

        if (resenia.getComentario() != null
                && !resenia.getComentario().isBlank()) {
            reseniaFound.setComentario(resenia.getComentario());
        }

        if (resenia.getFechaResenia() != null) {
            reseniaFound.setFechaResenia(resenia.getFechaResenia());
        }

        if (resenia.getEvento() != null) {
            reseniaFound.setEvento(resenia.getEvento());
        }

        if (resenia.getArtista() != null) {
            reseniaFound.setArtista(resenia.getArtista());
        }

        if (resenia.getRestaurante() != null) {
            reseniaFound.setRestaurante(resenia.getRestaurante());
        }

        return reseniaRepository.save(reseniaFound);
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<Resenia> findByEventoId(Long eventoId) {
        return reseniaRepository.findByEvento_Id(eventoId);
    }

    @Override
    public List<Resenia> findByArtistaId(Long artistaId) {
        return reseniaRepository.findByArtista_Id(artistaId);
    }

    @Override
    public List<Resenia> findByRestauranteId(Long restauranteId) {
        return reseniaRepository.findByRestaurante_Id(restauranteId);
    }

    // ----------------------------------------------------
    // SQL Nativo
    // ----------------------------------------------------

    @Override
    public List<Resenia> findByEventoSQL(Long eventoId) {
        return reseniaRepository.findByEventoSQL(eventoId);
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<Resenia> findByEventoJPQL(Long eventoId) {
        return reseniaRepository.findByEventoJPQL(eventoId);
    }
}
