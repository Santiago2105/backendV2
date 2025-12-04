package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Postulacion;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.PostulacionRepository;
import pe.edu.upc.backend.services.PostulacionService;

import java.util.List;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public Postulacion add(Postulacion postulacion) {

        // Validación de campos requeridos
        if (postulacion.getMensaje() == null || postulacion.getMensaje().isBlank()) {
            throw new RequiredDataException("El mensaje de la postulación no puede ser nulo o vacío.");
        }
        if (postulacion.getAnuncio() == null) {
            throw new RequiredDataException("La postulación debe estar asociada a un anuncio.");
        }
        if (postulacion.getArtista() == null) {
            throw new RequiredDataException("La postulación debe estar asociada a un artista.");
        }

        return postulacionRepository.save(postulacion);
    }

    @Override
    public void delete(Long id) {
        Postulacion postulacionFound = findById(id);

        if (postulacionFound == null) {
            throw new ResourceNotFoundException("No se encontró la postulación con id: " + id);
        }

        postulacionRepository.deleteById(id);
    }

    @Override
    public Postulacion findById(Long id) {
        return postulacionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Postulacion> listAll() {
        return postulacionRepository.findAll();
    }

    @Override
    public Postulacion edit(Postulacion postulacion) {

        Postulacion postulacionFound = findById(postulacion.getId());
        if (postulacionFound == null) {
            return null;
        }

        // Actualización de atributos
        if (postulacion.getMensaje() != null && !postulacion.getMensaje().isBlank()) {
            postulacionFound.setMensaje(postulacion.getMensaje());
        }

        postulacionFound.setAceptada(postulacion.isAceptada());

        if (postulacion.getFechaPostulacion() != null) {
            postulacionFound.setFechaPostulacion(postulacion.getFechaPostulacion());
        }

        if (postulacion.getAnuncio() != null) {
            postulacionFound.setAnuncio(postulacion.getAnuncio());
        }

        if (postulacion.getArtista() != null) {
            postulacionFound.setArtista(postulacion.getArtista());
        }

        return postulacionRepository.save(postulacionFound);
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<Postulacion> findByAnuncioId(Long anuncioId) {
        return postulacionRepository.findByAnuncio_Id(anuncioId);
    }

    @Override
    public List<Postulacion> findByArtistaId(Long artistaId) {
        return postulacionRepository.findByArtista_Id(artistaId);
    }

    @Override
    public List<Postulacion> findByAceptada(boolean aceptada) {
        return postulacionRepository.findByAceptada(aceptada);
    }

    // ----------------------------------------------------
    // SQL Nativo
    // ----------------------------------------------------

    @Override
    public List<Postulacion> findByAnuncioSQL(Long anuncioId) {
        return postulacionRepository.findByAnuncioSQL(anuncioId);
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<Postulacion> findByAnuncioJPQL(Long anuncioId) {
        return postulacionRepository.findByAnuncioJPQL(anuncioId);
    }
}
