package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.ArtistaDTO;
import pe.edu.upc.backend.entities.Artista;

import java.util.List;

public interface ArtistaService {

    // CRUD
    ArtistaDTO add(ArtistaDTO artista);

    void delete(Long id);

    Artista findById(Long id);

    List<Artista> listAll();

    Artista edit(Artista artista);


    // Query Methods (del repositorio)
    List<Artista> findByGeneroPrincipal(String genero);

    List<Artista> findByCiudad(String ciudad);

    List<Artista> findByUsuarioId(Long usuarioId);


    // SQL Nativo
    List<Artista> findByCiudadSQL(String ciudad);


    // JPQL
    List<Artista> findByCiudadJPQL(String ciudad);
}
