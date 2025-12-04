package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Mensaje;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.MensajeRepository;
import pe.edu.upc.backend.services.MensajeService;

import java.util.List;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public Mensaje add(Mensaje mensaje) {

        // Validación de campos requeridos
        if (mensaje.getTexto() == null || mensaje.getTexto().isBlank()) {
            throw new RequiredDataException("El texto del mensaje no puede ser nulo o vacío.");
        }
        if (mensaje.getAnuncio() == null) {
            throw new RequiredDataException("El mensaje debe estar asociado a un anuncio.");
        }
        if (mensaje.getUsuario() == null) {
            throw new RequiredDataException("El mensaje debe estar asociado a un usuario.");
        }

        return mensajeRepository.save(mensaje);
    }

    @Override
    public void delete(Long id) {
        Mensaje mensajeFound = findById(id);

        if (mensajeFound == null) {
            throw new ResourceNotFoundException("No se encontró el mensaje con id: " + id);
        }

        mensajeRepository.deleteById(id);
    }

    @Override
    public Mensaje findById(Long id) {
        return mensajeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Mensaje> listAll() {
        return mensajeRepository.findAll();
    }

    @Override
    public Mensaje edit(Mensaje mensaje) {

        Mensaje mensajeFound = findById(mensaje.getId());
        if (mensajeFound == null) {
            return null;
        }

        // Actualización de atributos
        if (mensaje.getTexto() != null && !mensaje.getTexto().isBlank()) {
            mensajeFound.setTexto(mensaje.getTexto());
        }

        if (mensaje.getFechaEnvio() != null) {
            mensajeFound.setFechaEnvio(mensaje.getFechaEnvio());
        }

        if (mensaje.getAnuncio() != null) {
            mensajeFound.setAnuncio(mensaje.getAnuncio());
        }

        if (mensaje.getUsuario() != null) {
            mensajeFound.setUsuario(mensaje.getUsuario());
        }

        return mensajeRepository.save(mensajeFound);
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<Mensaje> findByAnuncioId(Long anuncioId) {
        return mensajeRepository.findByAnuncio_Id(anuncioId);
    }

    @Override
    public List<Mensaje> findByUsuarioId(Long usuarioId) {
        return mensajeRepository.findByUsuario_Id(usuarioId);
    }

    // ----------------------------------------------------
    // SQL Nativo
    // ----------------------------------------------------

    @Override
    public List<Mensaje> findByAnuncioSQL(Long anuncioId) {
        return mensajeRepository.findByAnuncioSQL(anuncioId);
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<Mensaje> findByAnuncioJPQL(Long anuncioId) {
        return mensajeRepository.findByAnuncioJPQL(anuncioId);
    }
}
