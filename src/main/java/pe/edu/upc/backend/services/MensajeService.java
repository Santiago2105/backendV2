package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Mensaje;

import java.util.List;

public interface MensajeService {

    // CRUD
    Mensaje add(Mensaje mensaje);

    void delete(Long id);

    Mensaje findById(Long id);

    List<Mensaje> listAll();

    Mensaje edit(Mensaje mensaje);


    // Query Methods (del repositorio)
    List<Mensaje> findByAnuncioId(Long anuncioId);

    List<Mensaje> findByUsuarioId(Long usuarioId);


    // SQL Nativo
    List<Mensaje> findByAnuncioSQL(Long anuncioId);


    // JPQL
    List<Mensaje> findByAnuncioJPQL(Long anuncioId);
}
