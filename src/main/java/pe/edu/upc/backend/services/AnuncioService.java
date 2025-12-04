package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Anuncio;

import java.util.List;

public interface AnuncioService {

    // CRUD
    Anuncio add(Anuncio anuncio);

    void delete(Long id);

    Anuncio findById(Long id);

    List<Anuncio> listAll();

    Anuncio edit(Anuncio anuncio);



    // Query Methods (del repositorio)
    List<Anuncio> findByEventoId(Long eventoId);

    List<Anuncio> findByActivo(boolean activo);

    List<Anuncio> findByGeneroBuscado(String genero);


    // SQL Nativo
    List<Anuncio> findByEventoSQL(Long eventoId);


    // JPQL
    List<Anuncio> findByEventoJPQL(Long eventoId);
}
