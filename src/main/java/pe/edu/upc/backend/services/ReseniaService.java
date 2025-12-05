package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.ReseniaDTO;
import java.util.List;

public interface ReseniaService {

    ReseniaDTO add(ReseniaDTO reseniaDTO);

    ReseniaDTO update(Long id, ReseniaDTO reseniaDTO);

    void delete(Long id);

    ReseniaDTO findById(Long id);

    List<ReseniaDTO> listAll();

    List<ReseniaDTO> findByEventoId(Long eventoId);

    List<ReseniaDTO> findByArtistaId(Long artistaId);

    List<ReseniaDTO> findByRestauranteId(Long restauranteId);

    List<ReseniaDTO> findByEventoSQL(Long eventoId);

    List<ReseniaDTO> findByEventoJPQL(Long eventoId);
}