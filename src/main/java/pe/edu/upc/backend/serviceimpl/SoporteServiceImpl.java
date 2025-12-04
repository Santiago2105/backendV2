package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Soporte;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.SoporteRepository;
import pe.edu.upc.backend.services.SoporteService;

import java.util.List;

@Service
public class SoporteServiceImpl implements SoporteService {

    @Autowired
    private SoporteRepository soporteRepository;

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public Soporte add(Soporte soporte) {

        // Validación de campos obligatorios
        if (soporte.getAsunto() == null || soporte.getAsunto().isBlank()) {
            throw new RequiredDataException("El asunto del ticket no puede ser nulo o vacío.");
        }
        if (soporte.getDescripcion() == null || soporte.getDescripcion().isBlank()) {
            throw new RequiredDataException("La descripción del ticket no puede ser nula o vacía.");
        }
        if (soporte.getUsuario() == null) {
            throw new RequiredDataException("El ticket de soporte debe estar asociado a un usuario.");
        }

        return soporteRepository.save(soporte);
    }

    @Override
    public void delete(Long id) {
        Soporte soporteFound = findById(id);

        if (soporteFound == null) {
            throw new ResourceNotFoundException("No se encontró el ticket de soporte con id: " + id);
        }

        soporteRepository.deleteById(id);
    }

    @Override
    public Soporte findById(Long id) {
        return soporteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Soporte> listAll() {
        return soporteRepository.findAll();
    }

    @Override
    public Soporte edit(Soporte soporte) {

        Soporte soporteFound = findById(soporte.getId());
        if (soporteFound == null) {
            return null;
        }

        // Actualización de atributos
        if (soporte.getAsunto() != null && !soporte.getAsunto().isBlank()) {
            soporteFound.setAsunto(soporte.getAsunto());
        }

        if (soporte.getDescripcion() != null && !soporte.getDescripcion().isBlank()) {
            soporteFound.setDescripcion(soporte.getDescripcion());
        }

        soporteFound.setEstado(soporte.isEstado());

        if (soporte.getFechaCreacion() != null) {
            soporteFound.setFechaCreacion(soporte.getFechaCreacion());
        }

        if (soporte.getFechaCierre() != null) {
            soporteFound.setFechaCierre(soporte.getFechaCierre());
        }

        if (soporte.getUsuario() != null) {
            soporteFound.setUsuario(soporte.getUsuario());
        }

        return soporteRepository.save(soporteFound);
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<Soporte> findByEstado(boolean estado) {
        return soporteRepository.findByEstado(estado);
    }

    @Override
    public List<Soporte> findByUsuarioId(Long usuarioId) {
        return soporteRepository.findByUsuario_Id(usuarioId);
    }

    // ----------------------------------------------------
    // SQL Nativo
    // ----------------------------------------------------

    @Override
    public List<Soporte> findByEstadoSQL(boolean estado) {
        return soporteRepository.findByEstadoSQL(estado);
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<Soporte> findByEstadoJPQL(boolean estado) {
        return soporteRepository.findByEstadoJPQL(estado);
    }
}
