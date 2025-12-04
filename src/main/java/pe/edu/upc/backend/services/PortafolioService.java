package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Portafolio;

import java.util.List;

public interface PortafolioService {

    // CRUD
    Portafolio add(Portafolio portafolio);

    void delete(Long id);

    Portafolio findById(Long id);

    List<Portafolio> listAll();

    Portafolio edit(Portafolio portafolio);


    // Query Methods (del repositorio)
    List<Portafolio> findByArtistaId(Long artistaId);

    List<Portafolio> findByTipo(String tipo);

    List<Portafolio> findByTitulo(String titulo);


    // SQL Nativo
    List<Portafolio> findByArtistaSQL(Long artistaId);


    // JPQL
    List<Portafolio> findByArtistaJPQL(Long artistaId);
}
