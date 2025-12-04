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
import java.util.stream.Collectors;

@Service
public class ArtistaServiceImpl implements ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private UserRepository userRepository;

    // ----------------------------------------------------
    // Helpers de mapeo
    // ----------------------------------------------------

    private ArtistaDTO toDTO(Artista artista) {
        if (artista == null) return null;

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

    private Artista toEntity(ArtistaDTO dto) {
        Artista artista = new Artista();

        artista.setId(dto.getId());
        artista.setNombreArtistico(dto.getNombreArtistico());
        artista.setGeneroPrincipal(dto.getGeneroPrincipal());
        artista.setBio(dto.getBio());
        artista.setCiudad(dto.getCiudad());

        if (dto.getUsuarioId() == null) {
            throw new RequiredDataException("El id del usuario es obligatorio para el artista");
        }

        User user = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con id: " + dto.getUsuarioId()
                ));
        artista.setUsuario(user);

        return artista;
    }

    private List<ArtistaDTO> toDTOList(List<Artista> lista) {
        return lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ----------------------------------------------------
    // Implementación de la interfaz
    // ----------------------------------------------------

    @Override
    public ArtistaDTO add(ArtistaDTO artistaDTO) {
        if (artistaDTO.getNombreArtistico() == null || artistaDTO.getNombreArtistico().isBlank()) {
            throw new RequiredDataException("El nombre artístico es obligatorio");
        }

        Artista artista = toEntity(artistaDTO);
        Artista saved = artistaRepository.save(artista);
        return toDTO(saved);
    }

    @Override
    public ArtistaDTO update(Long id, ArtistaDTO artistaDTO) {
        Artista existing = artistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + id));

        // Actualizar campos básicos
        existing.setNombreArtistico(artistaDTO.getNombreArtistico());
        existing.setGeneroPrincipal(artistaDTO.getGeneroPrincipal());
        existing.setBio(artistaDTO.getBio());
        existing.setCiudad(artistaDTO.getCiudad());

        // Actualizar usuario (si viene)
        if (artistaDTO.getUsuarioId() != null) {
            User user = userRepository.findById(artistaDTO.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Usuario no encontrado con id: " + artistaDTO.getUsuarioId()
                    ));
            existing.setUsuario(user);
        }

        Artista updated = artistaRepository.save(existing);
        return toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Artista existing = artistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + id));
        artistaRepository.delete(existing);
    }

    @Override
    public ArtistaDTO findById(Long id) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + id));
        return toDTO(artista);
    }

    @Override
    public List<ArtistaDTO> listAll() {
        return toDTOList(artistaRepository.findAll());
    }

    @Override
    public List<ArtistaDTO> findByGeneroPrincipal(String genero) {
        return toDTOList(artistaRepository.findByGeneroPrincipal(genero));
    }

    @Override
    public List<ArtistaDTO> findByCiudad(String ciudad) {
        return toDTOList(artistaRepository.findByCiudad(ciudad));
    }

    @Override
    public List<ArtistaDTO> findByUsuarioId(Long usuarioId) {
        return toDTOList(artistaRepository.findByUsuario_Id(usuarioId));
    }

    @Override
    public List<ArtistaDTO> findByCiudadSQL(String ciudad) {
        return toDTOList(artistaRepository.findByCiudadSQL(ciudad));
    }

    @Override
    public List<ArtistaDTO> findByCiudadJPQL(String ciudad) {
        return toDTOList(artistaRepository.findByCiudadJPQL(ciudad));
    }
}
