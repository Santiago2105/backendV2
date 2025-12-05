package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.ReseniaDTO;
import pe.edu.upc.backend.entities.Artista;
import pe.edu.upc.backend.entities.Evento;
import pe.edu.upc.backend.entities.Resenia;
import pe.edu.upc.backend.entities.Restaurante;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.ArtistaRepository;
import pe.edu.upc.backend.repositories.EventoRepository;
import pe.edu.upc.backend.repositories.ReseniaRepository;
import pe.edu.upc.backend.repositories.RestauranteRepository;
import pe.edu.upc.backend.services.ReseniaService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReseniaServiceImpl implements ReseniaService {

    @Autowired
    private ReseniaRepository reseniaRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private ArtistaRepository artistaRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;

    // --- Mappers ---
    private ReseniaDTO toDTO(Resenia resenia) {
        if (resenia == null) return null;
        ReseniaDTO dto = new ReseniaDTO();
        dto.setId(resenia.getId());
        dto.setPuntuacion(resenia.getPuntuacion());
        dto.setComentario(resenia.getComentario());
        dto.setFechaResenia(resenia.getFechaResenia());

        if (resenia.getEvento() != null) dto.setEventoId(resenia.getEvento().getId());
        if (resenia.getArtista() != null) dto.setArtistaId(resenia.getArtista().getId());
        if (resenia.getRestaurante() != null) dto.setRestauranteId(resenia.getRestaurante().getId());

        return dto;
    }

    private Resenia toEntity(ReseniaDTO dto) {
        Resenia resenia = new Resenia();
        resenia.setPuntuacion(dto.getPuntuacion());
        resenia.setComentario(dto.getComentario());

        if(dto.getFechaResenia() != null) resenia.setFechaResenia(dto.getFechaResenia());
        else resenia.setFechaResenia(LocalDate.now());

        if (dto.getEventoId() != null) {
            Evento evento = eventoRepository.findById(dto.getEventoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));
            resenia.setEvento(evento);
        }
        if (dto.getArtistaId() != null) {
            Artista artista = artistaRepository.findById(dto.getArtistaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado"));
            resenia.setArtista(artista);
        }
        if (dto.getRestauranteId() != null) {
            Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurante no encontrado"));
            resenia.setRestaurante(restaurante);
        }
        return resenia;
    }

    private List<ReseniaDTO> toDTOList(List<Resenia> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // --- CRUD ---
    @Override
    public ReseniaDTO add(ReseniaDTO dto) {
        if (dto.getPuntuacion() == null || !dto.getPuntuacion().matches("[A-E]")) {
            throw new RequiredDataException("Puntuación inválida (A-E).");
        }
        Resenia entity = toEntity(dto);
        return toDTO(reseniaRepository.save(entity));
    }

    @Override
    public ReseniaDTO update(Long id, ReseniaDTO dto) {
        Resenia entity = reseniaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reseña no encontrada"));

        entity.setPuntuacion(dto.getPuntuacion());
        entity.setComentario(dto.getComentario());

        return toDTO(reseniaRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if(!reseniaRepository.existsById(id)) throw new ResourceNotFoundException("No encontrado");
        reseniaRepository.deleteById(id);
    }

    @Override
    public ReseniaDTO findById(Long id) {
        return toDTO(reseniaRepository.findById(id).orElse(null));
    }

    @Override
    public List<ReseniaDTO> listAll() {
        return toDTOList(reseniaRepository.findAll());
    }

    // --- Queries ---
    @Override
    public List<ReseniaDTO> findByEventoId(Long eventoId) {
        return toDTOList(reseniaRepository.findByEvento_Id(eventoId));
    }

    @Override
    public List<ReseniaDTO> findByArtistaId(Long artistaId) {
        return toDTOList(reseniaRepository.findByArtista_Id(artistaId));
    }

    @Override
    public List<ReseniaDTO> findByRestauranteId(Long restauranteId) {
        return toDTOList(reseniaRepository.findByRestaurante_Id(restauranteId));
    }

    @Override
    public List<ReseniaDTO> findByEventoSQL(Long eventoId) {
        return toDTOList(reseniaRepository.findByEventoSQL(eventoId));
    }

    @Override
    public List<ReseniaDTO> findByEventoJPQL(Long eventoId) {
        return toDTOList(reseniaRepository.findByEventoJPQL(eventoId));
    }
}