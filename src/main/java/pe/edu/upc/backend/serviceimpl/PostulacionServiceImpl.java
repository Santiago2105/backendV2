package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.PostulacionDTO;
import pe.edu.upc.backend.entities.Anuncio;
import pe.edu.upc.backend.entities.Artista;
import pe.edu.upc.backend.entities.Postulacion;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.AnuncioRepository;
import pe.edu.upc.backend.repositories.ArtistaRepository;
import pe.edu.upc.backend.repositories.PostulacionRepository;
import pe.edu.upc.backend.services.PostulacionService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;
    @Autowired
    private AnuncioRepository anuncioRepository;
    @Autowired
    private ArtistaRepository artistaRepository;

    // --- Mappers ---
    private PostulacionDTO toDTO(Postulacion postulacion) {
        if (postulacion == null) return null;
        PostulacionDTO dto = new PostulacionDTO();
        dto.setId(postulacion.getId());
        dto.setMensaje(postulacion.getMensaje());
        dto.setAceptada(postulacion.isAceptada());
        dto.setFechaPostulacion(postulacion.getFechaPostulacion());

        if (postulacion.getAnuncio() != null) dto.setAnuncioId(postulacion.getAnuncio().getId());
        if (postulacion.getArtista() != null) dto.setArtistaId(postulacion.getArtista().getId());
        return dto;
    }

    private Postulacion toEntity(PostulacionDTO dto) {
        Postulacion postulacion = new Postulacion();
        postulacion.setMensaje(dto.getMensaje());
        postulacion.setAceptada(dto.isAceptada());

        if (dto.getFechaPostulacion() != null) postulacion.setFechaPostulacion(dto.getFechaPostulacion());
        else postulacion.setFechaPostulacion(LocalDate.now());

        if (dto.getAnuncioId() != null) {
            Anuncio anuncio = anuncioRepository.findById(dto.getAnuncioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Anuncio no encontrado"));
            postulacion.setAnuncio(anuncio);
        }
        if (dto.getArtistaId() != null) {
            Artista artista = artistaRepository.findById(dto.getArtistaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado"));
            postulacion.setArtista(artista);
        }
        return postulacion;
    }

    private List<PostulacionDTO> toDTOList(List<Postulacion> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // --- CRUD ---
    @Override
    public PostulacionDTO add(PostulacionDTO dto) {
        if (dto.getMensaje() == null || dto.getMensaje().isBlank()) {
            throw new RequiredDataException("Mensaje requerido.");
        }
        if (dto.getAnuncioId() == null || dto.getArtistaId() == null) {
            throw new RequiredDataException("Faltan datos de relación (anuncio/artista).");
        }

        Postulacion entity = toEntity(dto);
        // Default: no aceptada al inicio
        entity.setAceptada(false);
        return toDTO(postulacionRepository.save(entity));
    }

    @Override
    public PostulacionDTO update(Long id, PostulacionDTO dto) {
        Postulacion entity = postulacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postulación no encontrada"));

        if (dto.getMensaje() != null) entity.setMensaje(dto.getMensaje());
        // Aquí es donde un restaurante aceptaría la postulación
        entity.setAceptada(dto.isAceptada());

        return toDTO(postulacionRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if(!postulacionRepository.existsById(id)) throw new ResourceNotFoundException("No encontrado");
        postulacionRepository.deleteById(id);
    }

    @Override
    public PostulacionDTO findById(Long id) {
        return toDTO(postulacionRepository.findById(id).orElse(null));
    }

    @Override
    public List<PostulacionDTO> listAll() {
        return toDTOList(postulacionRepository.findAll());
    }

    // --- Queries ---
    @Override
    public List<PostulacionDTO> findByAnuncioId(Long anuncioId) {
        return toDTOList(postulacionRepository.findByAnuncio_Id(anuncioId));
    }

    @Override
    public List<PostulacionDTO> findByArtistaId(Long artistaId) {
        return toDTOList(postulacionRepository.findByArtista_Id(artistaId));
    }

    @Override
    public List<PostulacionDTO> findByAceptada(boolean aceptada) {
        return toDTOList(postulacionRepository.findByAceptada(aceptada));
    }

    @Override
    public List<PostulacionDTO> findByAnuncioSQL(Long anuncioId) {
        return toDTOList(postulacionRepository.findByAnuncioSQL(anuncioId));
    }

    @Override
    public List<PostulacionDTO> findByAnuncioJPQL(Long anuncioId) {
        return toDTOList(postulacionRepository.findByAnuncioJPQL(anuncioId));
    }
}