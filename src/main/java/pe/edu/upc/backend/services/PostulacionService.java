package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Postulacion;

import java.util.List;

public interface PostulacionService {

    // CRUD
    Postulacion add(Postulacion postulacion);

    void delete(Long id);

    Postulacion findById(Long id);

    List<Postulacion> listAll();

    Postulacion edit(Postulacion postulacion);


    // Query Methods (del repositorio)
    List<Postulacion> findByAnuncioId(Long anuncioId);

    List<Postulacion> findByArtistaId(Long artistaId);

    List<Postulacion> findByAceptada(boolean aceptada);


    // SQL Nativo
    List<Postulacion> findByAnuncioSQL(Long anuncioId);


    // JPQL
    List<Postulacion> findByAnuncioJPQL(Long anuncioId);
}