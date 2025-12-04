package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Restaurante;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.RestauranteRepository;
import pe.edu.upc.backend.services.RestauranteService;

import java.util.List;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    // ----------------------------------------------------
    // CRUD
    // ----------------------------------------------------

    @Override
    public Restaurante add(Restaurante restaurante) {

        // Validación de campos requeridos
        if (restaurante.getNombre() == null || restaurante.getNombre().isBlank()) {
            throw new RequiredDataException("El nombre del restaurante no puede ser nulo o vacío.");
        }
        if (restaurante.getDireccion() == null || restaurante.getDireccion().isBlank()) {
            throw new RequiredDataException("La dirección no puede ser nula o vacía.");
        }
        if (restaurante.getCiudad() == null || restaurante.getCiudad().isBlank()) {
            throw new RequiredDataException("La ciudad no puede ser nula o vacía.");
        }

        // Persistencia del registro
        return restauranteRepository.save(restaurante);
    }

    @Override
    public void delete(Long id) {
        Restaurante restauranteFound = findById(id);

        if (restauranteFound == null) {
            throw new ResourceNotFoundException("No se encontró el restaurante con id: " + id);
        }

        restauranteRepository.deleteById(id);
    }

    @Override
    public Restaurante findById(Long id) {
        return restauranteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurante> listAll() {
        return restauranteRepository.findAll();
    }

    @Override
    public Restaurante edit(Restaurante restaurante) {

        Restaurante restauranteFound = findById(restaurante.getId());
        if (restauranteFound == null) {
            return null;
        }

        // Actualización de atributos con valores válidos
        if (restaurante.getNombre() != null && !restaurante.getNombre().isBlank()) {
            restauranteFound.setNombre(restaurante.getNombre());
        }

        if (restaurante.getDireccion() != null && !restaurante.getDireccion().isBlank()) {
            restauranteFound.setDireccion(restaurante.getDireccion());
        }

        if (restaurante.getCiudad() != null && !restaurante.getCiudad().isBlank()) {
            restauranteFound.setCiudad(restaurante.getCiudad());
        }

        if (restaurante.getAforoMesas() != null && restaurante.getAforoMesas() > 0) {
            restauranteFound.setAforoMesas(restaurante.getAforoMesas());
        }

        if (restaurante.getUsuario() != null) {
            restauranteFound.setUsuario(restaurante.getUsuario());
        }

        return restauranteRepository.save(restauranteFound);
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<Restaurante> findByCiudad(String ciudad) {
        return restauranteRepository.findByCiudad(ciudad);
    }

    @Override
    public List<Restaurante> findByAforoMesasGreaterThan(Integer aforo) {
        return restauranteRepository.findByAforoMesasGreaterThan(aforo);
    }

    @Override
    public List<Restaurante> findByUsuarioId(Long usuarioId) {
        return restauranteRepository.findByUsuario_Id(usuarioId);
    }

    // ----------------------------------------------------
    // SQL Nativo
    // ----------------------------------------------------

    @Override
    public List<Restaurante> findByCiudadSQL(String ciudad) {
        return restauranteRepository.findByCiudadSQL(ciudad);
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<Restaurante> findByCiudadJPQL(String ciudad) {
        return restauranteRepository.findByCiudadJPQL(ciudad);
    }
}
