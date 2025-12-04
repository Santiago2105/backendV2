package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Evento;

import java.time.LocalDate;
import java.util.List;

public interface EventoService {

    // CRUD
    Evento add(Evento evento);

    void delete(Long id);

    Evento findById(Long id);

    List<Evento> listAll();

    Evento edit(Evento evento);


    // Query Methods (del repositorio)
    List<Evento> findByRestauranteId(Long restauranteId);

    List<Evento> findByArtistaId(Long artistaId);

    List<Evento> findByFechaEvento(LocalDate fechaEvento);


    // SQL Nativo
    List<Evento> findByRestauranteSQL(Long restauranteId);


    // JPQL
    List<Evento> findByRestauranteJPQL(Long restauranteId);
}
