package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Portafolio;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.PortafolioRepository;
import pe.edu.upc.backend.services.PortafolioService;

import java.util.List;

@Service
public class PortafolioServiceImpl implements PortafolioService {

    @Autowired
    private PortafolioRepository portafolioRepository;

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public Portafolio add(Portafolio portafolio) {

        // Validación de campos obligatorios
        if (portafolio.getTitulo() == null || portafolio.getTitulo().isBlank()) {
            throw new RequiredDataException("El título del portafolio no puede ser nulo o vacío.");
        }
        if (portafolio.getTipo() == null || portafolio.getTipo().isBlank()) {
            throw new RequiredDataException("El tipo de portafolio no puede ser nulo o vacío.");
        }
        if (portafolio.getUrl() == null || portafolio.getUrl().isBlank()) {
            throw new RequiredDataException("La URL del portafolio no puede ser nula o vacía.");
        }
        if (portafolio.getArtista() == null) {
            throw new RequiredDataException("El portafolio debe estar asociado a un artista.");
        }

        return portafolioRepository.save(portafolio);
    }

    @Override
    public void delete(Long id) {
        Portafolio portafolioFound = findById(id);

        if (portafolioFound == null) {
            throw new ResourceNotFoundException("No se encontró el portafolio con id: " + id);
        }

        portafolioRepository.deleteById(id);
    }

    @Override
    public Portafolio findById(Long id) {
        return portafolioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Portafolio> listAll() {
        return portafolioRepository.findAll();
    }

    @Override
    public Portafolio edit(Portafolio portafolio) {

        Portafolio portafolioFound = findById(portafolio.getId());
        if (portafolioFound == null) {
            return null;
        }

        // Actualización de atributos
        if (portafolio.getTitulo() != null && !portafolio.getTitulo().isBlank()) {
            portafolioFound.setTitulo(portafolio.getTitulo());
        }

        if (portafolio.getTipo() != null && !portafolio.getTipo().isBlank()) {
            portafolioFound.setTipo(portafolio.getTipo());
        }

        if (portafolio.getUrl() != null && !portafolio.getUrl().isBlank()) {
            portafolioFound.setUrl(portafolio.getUrl());
        }

        if (portafolio.getFechaCreacion() != null) {
            portafolioFound.setFechaCreacion(portafolio.getFechaCreacion());
        }

        if (portafolio.getArtista() != null) {
            portafolioFound.setArtista(portafolio.getArtista());
        }

        return portafolioRepository.save(portafolioFound);
    }

    // ----------------------------------------------------
    // QUERY METHODS
    // ----------------------------------------------------

    @Override
    public List<Portafolio> findByArtistaId(Long artistaId) {
        return portafolioRepository.findByArtista_Id(artistaId);
    }

    @Override
    public List<Portafolio> findByTipo(String tipo) {
        return portafolioRepository.findByTipo(tipo);
    }

    @Override
    public List<Portafolio> findByTitulo(String titulo) {
        return portafolioRepository.findByTitulo(titulo);
    }

    // ----------------------------------------------------
    // SQL NATIVO
    // ----------------------------------------------------

    @Override
    public List<Portafolio> findByArtistaSQL(Long artistaId) {
        return portafolioRepository.findByArtistaSQL(artistaId);
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<Portafolio> findByArtistaJPQL(Long artistaId) {
        return portafolioRepository.findByArtistaJPQL(artistaId);
    }
}
