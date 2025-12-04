package pe.edu.upc.backend.services;
import pe.edu.upc.backend.entities.Resenia;
import java.util.List;

public interface ReseniaService {

    // CRUD
    Resenia add(Resenia resenia);

    void delete(Long id);

    Resenia findById(Long id);

    List<Resenia> listAll();

    Resenia edit(Resenia resenia);


    // Query Methods (del repositorio)
    List<Resenia> findByEventoId(Long eventoId);

    List<Resenia> findByArtistaId(Long artistaId);

    List<Resenia> findByRestauranteId(Long restauranteId);


    // SQL Nativo
    List<Resenia> findByEventoSQL(Long eventoId);


    // JPQL
    List<Resenia> findByEventoJPQL(Long eventoId);
}
