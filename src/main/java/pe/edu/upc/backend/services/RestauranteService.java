package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.RestauranteDTO;
import java.util.List;

public interface RestauranteService {

    // CRUD
    RestauranteDTO add(RestauranteDTO restauranteDTO);

    RestauranteDTO update(Long id, RestauranteDTO restauranteDTO); // Cambiamos 'edit' por 'update' para ser consistentes

    void delete(Long id);

    RestauranteDTO findById(Long id);

    List<RestauranteDTO> listAll();

    // Query Methods
    List<RestauranteDTO> findByCiudad(String ciudad);

    List<RestauranteDTO> findByAforoMesasGreaterThan(Integer aforo);

    List<RestauranteDTO> findByUsuarioId(Long usuarioId);

    // SQL Nativo
    List<RestauranteDTO> findByCiudadSQL(String ciudad);

    // JPQL
    List<RestauranteDTO> findByCiudadJPQL(String ciudad);
}