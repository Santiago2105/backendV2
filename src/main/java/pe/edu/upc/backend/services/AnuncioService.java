package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.AnuncioDTO;
import java.util.List;

public interface AnuncioService {

    AnuncioDTO add(AnuncioDTO anuncioDTO);

    AnuncioDTO update(Long id, AnuncioDTO anuncioDTO);

    void delete(Long id);

    AnuncioDTO findById(Long id);

    List<AnuncioDTO> listAll();

    List<AnuncioDTO> findByEventoId(Long eventoId);

    List<AnuncioDTO> findByActivo(boolean activo);

    List<AnuncioDTO> findByGeneroBuscado(String genero);

    List<AnuncioDTO> findByEventoSQL(Long eventoId);

    List<AnuncioDTO> findByEventoJPQL(Long eventoId);
}