package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.ArtistaDTO;

import java.util.List;

public interface ArtistaService {

    // CRUD
    ArtistaDTO add(ArtistaDTO artistaDTO);

    ArtistaDTO update(Long id, ArtistaDTO artistaDTO);

    void delete(Long id);

    ArtistaDTO findById(Long id);

    List<ArtistaDTO> listAll();

    // Query Methods
    List<ArtistaDTO> findByGeneroPrincipal(String genero);

    List<ArtistaDTO> findByCiudad(String ciudad);

    List<ArtistaDTO> findByUsuarioId(Long usuarioId);

    // SQL Nativo
    List<ArtistaDTO> findByCiudadSQL(String ciudad);

    // JPQL
    List<ArtistaDTO> findByCiudadJPQL(String ciudad);
}
