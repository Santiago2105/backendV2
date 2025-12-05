package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.PortafolioDTO;
import java.util.List;

public interface PortafolioService {

    PortafolioDTO add(PortafolioDTO portafolioDTO);

    PortafolioDTO update(Long id, PortafolioDTO portafolioDTO);

    void delete(Long id);

    PortafolioDTO findById(Long id);

    List<PortafolioDTO> listAll();

    List<PortafolioDTO> findByArtistaId(Long artistaId);

    List<PortafolioDTO> findByTipo(String tipo);

    List<PortafolioDTO> findByTitulo(String titulo);

    List<PortafolioDTO> findByArtistaSQL(Long artistaId);

    List<PortafolioDTO> findByArtistaJPQL(Long artistaId);
}