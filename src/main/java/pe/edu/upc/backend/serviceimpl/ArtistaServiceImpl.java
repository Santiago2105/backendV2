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
    public ArtistaDTO add(ArtistaDTO artistaDTO) {

        // 1. Validaciones
        if (artistaDTO.getNombreArtistico() == null || artistaDTO.getNombreArtistico().isBlank()) {
            throw new RequiredDataException("El nombre artístico no puede ser nulo o vacío.");
        }
        if (artistaDTO.getGeneroPrincipal() == null || artistaDTO.getGeneroPrincipal().isBlank()) {
            throw new RequiredDataException("El género principal no puede ser nulo o vacío.");
        }
        if (artistaDTO.getCiudad() == null || artistaDTO.getCiudad().isBlank()) {
            throw new RequiredDataException("La ciudad no puede ser nula o vacía.");
        }

        // 2. Mapeo Manual: DTO -> Entidad
        // (Creamos la entidad que se guardará en BD)
        Artista artista = new Artista();
        artista.setNombreArtistico(artistaDTO.getNombreArtistico());
        artista.setGeneroPrincipal(artistaDTO.getGeneroPrincipal());
        artista.setBio(artistaDTO.getBio());
        artista.setCiudad(artistaDTO.getCiudad());

        // 3. Asignar Usuario (Corrección del bug)
        // Buscamos el usuario usando el ID que viene en el DTO
        if (artistaDTO.getUsuarioId() != null) {
            userRepository.findById(artistaDTO.getUsuarioId())
                    .ifPresent(usuario -> artista.setUsuario(usuario));
        }

        // 4. Guardar en Base de Datos
        Artista artistaGuardado = artistaRepository.save(artista);

        // 5. Retornar convertido a DTO (Usando tu función auxiliar)
        return convertirADTO(artistaGuardado);
    }

    @Override
    public void delete(Long id) {
        // Verificamos si existe directamente en la base de datos
        if (!artistaRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el artista con id: " + id);
        }
        // Si existe, lo borramos
        artistaRepository.deleteById(id);
    }

    @Override
    public ArtistaDTO findById(Long id) {
        // Buscamos la entidad en la BD
        Artista artista = artistaRepository.findById(id).orElse(null);

        // Si no existe, devolvemos null
        if (artista == null) {
            return null;
        }

        // Si existe, la convertimos a DTO y la devolvemos
        return convertirADTO(artista);
    }

    @Override
    public List<ArtistaDTO> listAll() {
        // Obtenemos la lista de entidades (Artista)
        List<Artista> listaEntidades = artistaRepository.findAll();

        // Transformamos cada entidad a DTO usando el método auxiliar
        return listaEntidades.stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    public ArtistaDTO edit(ArtistaDTO infoUpdate) {
        // 1. Buscamos la ENTIDAD REAL en la base de datos (no el DTO)
        Artista artista = artistaRepository.findById(infoUpdate.getId()).orElse(null);

        // Si no existe, retornamos null
        if (artista == null) {
            return null;
        }

        // 2. Actualizamos los campos de la entidad con los datos del DTO
        if (infoUpdate.getNombreArtistico() != null && !infoUpdate.getNombreArtistico().isBlank()) {
            artista.setNombreArtistico(infoUpdate.getNombreArtistico());
        }

        if (infoUpdate.getGeneroPrincipal() != null && !infoUpdate.getGeneroPrincipal().isBlank()) {
            artista.setGeneroPrincipal(infoUpdate.getGeneroPrincipal());
        }

        if (infoUpdate.getBio() != null && !infoUpdate.getBio().isBlank()) {
            artista.setBio(infoUpdate.getBio());
        }

        if (infoUpdate.getCiudad() != null && !infoUpdate.getCiudad().isBlank()) {
            artista.setCiudad(infoUpdate.getCiudad());
        }

        // Actualizamos el usuario (si viene en el DTO)
        if (infoUpdate.getUsuarioId() != null) {
            userRepository.findById(infoUpdate.getUsuarioId()).ifPresent(user -> {
                artista.setUsuario(user);
            });
        }

        // 3. Guardamos la entidad actualizada
        Artista artistaGuardado = artistaRepository.save(artista);

        // 4. Retornamos el DTO actualizado
        return convertirADTO(artistaGuardado);
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<ArtistaDTO> findByGeneroPrincipal(String genero) {
        // 1. Obtener lista de entidades del repo
        List<Artista> listaEntidades = artistaRepository.findByGeneroPrincipal(genero);

        // 2. Convertir usando nuestro método auxiliar
        return listaEntidades.stream().map(this::convertirADTO).toList(); // Referencia al método privado
    }

    @Override
    public List<ArtistaDTO> findByCiudad(String ciudad) {
        List<Artista> listaEntidades = artistaRepository.findByCiudad(ciudad);
        return listaEntidades.stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    public List<ArtistaDTO> findByUsuarioId(Long usuarioId) {
        List<Artista> listaEntidades = artistaRepository.findByUsuario_Id(usuarioId);
        return listaEntidades.stream()
                .map(this::convertirADTO)
                .toList();
    }
    // ----------------------------------------------------
    // SQL Nativo
    // ----------------------------------------------------

    @Override
    public List<ArtistaDTO> findByCiudadSQL(String ciudad) {
        List<Artista> listaEntidades = artistaRepository.findByCiudadSQL(ciudad);
        return listaEntidades.stream()
                .map(this::convertirADTO)
                .toList();
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<ArtistaDTO> findByCiudadJPQL(String ciudad) {
        List<Artista> listaEntidades = artistaRepository.findByCiudadJPQL(ciudad);
        return listaEntidades.stream()
                .map(this::convertirADTO)
                .toList();
    }

    // Método auxiliar para no repetir código
    private ArtistaDTO convertirADTO(Artista artista) {
        ArtistaDTO dto = new ArtistaDTO();
        dto.setId(artista.getId());
        dto.setNombreArtistico(artista.getNombreArtistico());
        dto.setGeneroPrincipal(artista.getGeneroPrincipal());
        dto.setBio(artista.getBio());
        dto.setCiudad(artista.getCiudad());

        if (artista.getUsuario() != null) {
            dto.setUsuarioId(artista.getUsuario().getId());
        }
        return dto;
    }
}
