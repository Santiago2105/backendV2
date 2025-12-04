package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Restaurante;

import java.util.List;

public interface RestauranteService {

    // CRUD
    Restaurante add(Restaurante restaurante);

    void delete(Long id);

    Restaurante findById(Long id);

    List<Restaurante> listAll();

    Restaurante edit(Restaurante restaurante);


    // Query Methods (del repositorio)
    List<Restaurante> findByCiudad(String ciudad);

    List<Restaurante> findByAforoMesasGreaterThan(Integer aforo);

    List<Restaurante> findByUsuarioId(Long usuarioId);


    // SQL Nativo
    List<Restaurante> findByCiudadSQL(String ciudad);


    // JPQL
    List<Restaurante> findByCiudadJPQL(String ciudad);
}
