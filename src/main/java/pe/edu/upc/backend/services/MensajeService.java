package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.MensajeDTO;
import java.util.List;

public interface MensajeService {

    MensajeDTO add(MensajeDTO mensajeDTO);

    MensajeDTO update(Long id, MensajeDTO mensajeDTO);

    void delete(Long id);

    MensajeDTO findById(Long id);

    List<MensajeDTO> listAll();

    List<MensajeDTO> findByAnuncioId(Long anuncioId);

    List<MensajeDTO> findByUsuarioId(Long usuarioId);

    List<MensajeDTO> findByAnuncioSQL(Long anuncioId);

    List<MensajeDTO> findByAnuncioJPQL(Long anuncioId);
}