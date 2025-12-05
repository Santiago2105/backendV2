package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.PortafolioDTO;
import pe.edu.upc.backend.entities.Artista;
import pe.edu.upc.backend.entities.Portafolio;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.ArtistaRepository;
import pe.edu.upc.backend.repositories.PortafolioRepository;
import pe.edu.upc.backend.services.PortafolioService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortafolioServiceImpl implements PortafolioService {

    @Autowired
    private PortafolioRepository portafolioRepository;
    @Autowired
    private ArtistaRepository artistaRepository;

    // --- Mappers ---
    private PortafolioDTO toDTO(Portafolio entity) {
        if (entity == null) return null;
        PortafolioDTO dto = new PortafolioDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setTipo(entity.getTipo());
        dto.setUrl(entity.getUrl());
        dto.setFechaCreacion(entity.getFechaCreacion());

        if (entity.getArtista() != null) {
            dto.setArtistaId(entity.getArtista().getId());
        }
        return dto;
    }

    private Portafolio toEntity(PortafolioDTO dto) {
        Portafolio entity = new Portafolio();
        entity.setTitulo(dto.getTitulo());
        entity.setTipo(dto.getTipo());
        entity.setUrl(dto.getUrl());

        if (dto.getFechaCreacion() != null) entity.setFechaCreacion(dto.getFechaCreacion());
        else entity.setFechaCreacion(LocalDate.now());

        if (dto.getArtistaId() != null) {
            Artista artista = artistaRepository.findById(dto.getArtistaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado id: " + dto.getArtistaId()));
            entity.setArtista(artista);
        }
        return entity;
    }

    private List<PortafolioDTO> toDTOList(List<Portafolio> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // --- CRUD ---
    @Override
    public PortafolioDTO add(PortafolioDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new RequiredDataException("Título requerido.");
        }
        if (dto.getArtistaId() == null) {
            throw new RequiredDataException("El portafolio debe pertenecer a un artista.");
        }

        Portafolio entity = toEntity(dto);
        return toDTO(portafolioRepository.save(entity));
    }

    @Override
    public PortafolioDTO update(Long id, PortafolioDTO dto) {
        Portafolio entity = portafolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Portafolio no encontrado id: " + id));

        entity.setTitulo(dto.getTitulo());
        entity.setTipo(dto.getTipo());
        entity.setUrl(dto.getUrl());

        // No cambiamos el artista usualmente en un update, pero si se requiere, agregar lógica aquí.

        return toDTO(portafolioRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!portafolioRepository.existsById(id)) {
            throw new ResourceNotFoundException("No encontrado id: " + id);
        }
        portafolioRepository.deleteById(id);
    }

    @Override
    public PortafolioDTO findById(Long id) {
        return toDTO(portafolioRepository.findById(id).orElse(null));
    }

    @Override
    public List<PortafolioDTO> listAll() {
        return toDTOList(portafolioRepository.findAll());
    }

    // --- Queries ---
    @Override
    public List<PortafolioDTO> findByArtistaId(Long artistaId) {
        return toDTOList(portafolioRepository.findByArtista_Id(artistaId));
    }

    @Override
    public List<PortafolioDTO> findByTipo(String tipo) {
        return toDTOList(portafolioRepository.findByTipo(tipo));
    }

    @Override
    public List<PortafolioDTO> findByTitulo(String titulo) {
        return toDTOList(portafolioRepository.findByTitulo(titulo));
    }

    @Override
    public List<PortafolioDTO> findByArtistaSQL(Long artistaId) {
        return toDTOList(portafolioRepository.findByArtistaSQL(artistaId));
    }

    @Override
    public List<PortafolioDTO> findByArtistaJPQL(Long artistaId) {
        return toDTOList(portafolioRepository.findByArtistaJPQL(artistaId));
    }
}