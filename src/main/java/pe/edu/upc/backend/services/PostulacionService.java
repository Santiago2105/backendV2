package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.PostulacionDTO;
import java.util.List;

public interface PostulacionService {

    PostulacionDTO add(PostulacionDTO postulacionDTO);

    PostulacionDTO update(Long id, PostulacionDTO postulacionDTO);

    void delete(Long id);

    PostulacionDTO findById(Long id);

    List<PostulacionDTO> listAll();

    List<PostulacionDTO> findByAnuncioId(Long anuncioId);

    List<PostulacionDTO> findByArtistaId(Long artistaId);

    List<PostulacionDTO> findByAceptada(boolean aceptada);

    List<PostulacionDTO> findByAnuncioSQL(Long anuncioId);

    List<PostulacionDTO> findByAnuncioJPQL(Long anuncioId);
}