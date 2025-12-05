package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.SoporteDTO;
import java.util.List;

public interface SoporteService {
    SoporteDTO add(SoporteDTO soporteDTO);
    SoporteDTO update(Long id, SoporteDTO soporteDTO);
    void delete(Long id);
    SoporteDTO findById(Long id);
    List<SoporteDTO> listAll();
    List<SoporteDTO> findByEstado(boolean estado);
    List<SoporteDTO> findByUsuarioId(Long usuarioId);
    List<SoporteDTO> findByEstadoSQL(boolean estado);
    List<SoporteDTO> findByEstadoJPQL(boolean estado);
}