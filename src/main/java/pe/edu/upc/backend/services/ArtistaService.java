package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.ArtistaDTO;
import pe.edu.upc.backend.entities.Artista;

import java.util.List;

public interface ArtistaService {

    // CRUD
    ArtistaDTO add(ArtistaDTO artista);

    void delete(Long id);

    ArtistaDTO findById(Long id);

    List<ArtistaDTO> listAll();

    ArtistaDTO edit(ArtistaDTO artista);


    // Query Methods (del repositorio)
    List<ArtistaDTO> findByGeneroPrincipal(String genero);

    List<ArtistaDTO> findByCiudad(String ciudad);

    List<ArtistaDTO> findByUsuarioId(Long usuarioId);


    // SQL Nativo
    List<ArtistaDTO> findByCiudadSQL(String ciudad);


    // JPQL
    List<ArtistaDTO> findByCiudadJPQL(String ciudad);
}
