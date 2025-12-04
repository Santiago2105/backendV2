package pe.edu.upc.backend.services;
import pe.edu.upc.backend.entities.Soporte;
import java.util.List;

public interface SoporteService {

    // CRUD
    Soporte add(Soporte soporte);

    void delete(Long id);

    Soporte findById(Long id);

    List<Soporte> listAll();

    Soporte edit(Soporte soporte);


    // Query Methods (del repositorio)
    List<Soporte> findByEstado(boolean estado);

    List<Soporte> findByUsuarioId(Long usuarioId);


    // SQL Nativo
    List<Soporte> findByEstadoSQL(boolean estado);


    // JPQL
    List<Soporte> findByEstadoJPQL(boolean estado);
}
