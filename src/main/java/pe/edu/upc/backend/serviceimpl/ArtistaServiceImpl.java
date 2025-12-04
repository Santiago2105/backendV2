package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.ArtistaDTO;
import pe.edu.upc.backend.entities.Artista;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.ArtistaRepository;
import pe.edu.upc.backend.repositories.UserRepository;
import pe.edu.upc.backend.services.ArtistaService;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaServiceImpl implements ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;
    @Autowired
    private UserRepository userRepository;

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public ArtistaDTO add(ArtistaDTO artista) {

        // Validación de campos obligatorios
        if (artista.getNombreArtistico() == null || artista.getNombreArtistico().isBlank()) {
            throw new RequiredDataException("El nombre artístico no puede ser nulo o vacío.");
        }
        if (artista.getGeneroPrincipal() == null || artista.getGeneroPrincipal().isBlank()) {
            throw new RequiredDataException("El género principal no puede ser nulo o vacío.");
        }
        if (artista.getCiudad() == null || artista.getCiudad().isBlank()) {
            throw new RequiredDataException("La ciudad no puede ser nula o vacía.");
        }

        // Persistencia del registro
        System.out.println(artista.getNombreArtistico());

        Artista artistaentidad = new Artista();
        artistaentidad.setBio(artista.getBio());
        artistaentidad.setNombreArtistico(artista.getNombreArtistico());
        artistaentidad.setGeneroPrincipal(artista.getGeneroPrincipal());
        artistaentidad.setCiudad(artista.getCiudad());
        Optional<User> usuario = userRepository.findById(artistaentidad.getId());
        artistaentidad.setUsuario(usuario.get());
        Artista artistaresponce =artistaRepository.save(artistaentidad);
        artista.setId(artistaresponce.getId());
        return artista;

    }

    @Override
    public void delete(Long id) {
        Artista artistaFound = findById(id);
        if (artistaFound == null) {
            throw new ResourceNotFoundException("No se encontró el artista con id: " + id);
        }

        // Eliminación del registro
        artistaRepository.deleteById(id);
    }

    @Override
    public Artista findById(Long id) {
        return artistaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Artista> listAll() {
        return artistaRepository.findAll();
    }

    @Override
    public Artista edit(Artista artista) {

        Artista artistaFound = findById(artista.getId());
        if (artistaFound == null) {
            return null;
        }

        // Actualización de valores proporcionados
        if (artista.getNombreArtistico() != null && !artista.getNombreArtistico().isBlank()) {
            artistaFound.setNombreArtistico(artista.getNombreArtistico());
        }

        if (artista.getGeneroPrincipal() != null && !artista.getGeneroPrincipal().isBlank()) {
            artistaFound.setGeneroPrincipal(artista.getGeneroPrincipal());
        }

        if (artista.getBio() != null && !artista.getBio().isBlank()) {
            artistaFound.setBio(artista.getBio());
        }

        if (artista.getCiudad() != null && !artista.getCiudad().isBlank()) {
            artistaFound.setCiudad(artista.getCiudad());
        }

        if (artista.getUsuario() != null) {
            artistaFound.setUsuario(artista.getUsuario());
        }

        return artistaRepository.save(artistaFound);
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<Artista> findByGeneroPrincipal(String genero) {
        return artistaRepository.findByGeneroPrincipal(genero);
    }

    @Override
    public List<Artista> findByCiudad(String ciudad) {
        return artistaRepository.findByCiudad(ciudad);
    }

    @Override
    public List<Artista> findByUsuarioId(Long usuarioId) {
        return artistaRepository.findByUsuario_Id(usuarioId);
    }

    // ----------------------------------------------------
    // SQL Nativo
    // ----------------------------------------------------

    @Override
    public List<Artista> findByCiudadSQL(String ciudad) {
        return artistaRepository.findByCiudadSQL(ciudad);
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<Artista> findByCiudadJPQL(String ciudad) {
        return artistaRepository.findByCiudadJPQL(ciudad);
    }
}
