package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.RestauranteDTO;
import pe.edu.upc.backend.entities.Restaurante;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.RestauranteRepository;
import pe.edu.upc.backend.repositories.UserRepository;
import pe.edu.upc.backend.services.RestauranteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private UserRepository userRepository;

    // ----------------------------------------------------
    // Métodos Auxiliares de Conversión (Mappers)
    // ----------------------------------------------------

    private RestauranteDTO toDTO(Restaurante restaurante) {
        if (restaurante == null) return null;

        RestauranteDTO dto = new RestauranteDTO();
        dto.setId(restaurante.getId());
        dto.setNombre(restaurante.getNombre());
        dto.setDireccion(restaurante.getDireccion());
        dto.setCiudad(restaurante.getCiudad());
        dto.setAforoMesas(restaurante.getAforoMesas());

        if (restaurante.getUsuario() != null) {
            dto.setUsuarioId(restaurante.getUsuario().getId());
        }
        return dto;
    }

    private Restaurante toEntity(RestauranteDTO dto) {
        Restaurante restaurante = new Restaurante();
        // Nota: No seteamos ID aquí si es un 'add', JPA lo genera.
        restaurante.setNombre(dto.getNombre());
        restaurante.setDireccion(dto.getDireccion());
        restaurante.setCiudad(dto.getCiudad());
        restaurante.setAforoMesas(dto.getAforoMesas());

        if (dto.getUsuarioId() != null) {
            User user = userRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + dto.getUsuarioId()));
            restaurante.setUsuario(user);
        }
        return restaurante;
    }

    private List<RestauranteDTO> toDTOList(List<Restaurante> lista) {
        return lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ----------------------------------------------------
    // Implementación CRUD
    // ----------------------------------------------------

    @Override
    public RestauranteDTO add(RestauranteDTO dto) {
        // Validaciones
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new RequiredDataException("El nombre del restaurante no puede ser nulo o vacío.");
        }
        if (dto.getDireccion() == null || dto.getDireccion().isBlank()) {
            throw new RequiredDataException("La dirección no puede ser nula o vacía.");
        }
        if (dto.getCiudad() == null || dto.getCiudad().isBlank()) {
            throw new RequiredDataException("La ciudad no puede ser nula o vacía.");
        }

        // Convertir, Guardar, Retornar
        Restaurante restaurante = toEntity(dto);
        return toDTO(restauranteRepository.save(restaurante));
    }

    @Override
    public RestauranteDTO update(Long id, RestauranteDTO dto) {
        // 1. Buscar Entidad Real
        Restaurante restauranteFound = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el restaurante con id: " + id));

        // 2. Actualizar campos
        restauranteFound.setNombre(dto.getNombre());
        restauranteFound.setDireccion(dto.getDireccion());
        restauranteFound.setCiudad(dto.getCiudad());

        if (dto.getAforoMesas() != null && dto.getAforoMesas() > 0) {
            restauranteFound.setAforoMesas(dto.getAforoMesas());
        }

        // 3. Actualizar Relación Usuario
        if (dto.getUsuarioId() != null) {
            User user = userRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + dto.getUsuarioId()));
            restauranteFound.setUsuario(user);
        }

        // 4. Guardar y Retornar
        return toDTO(restauranteRepository.save(restauranteFound));
    }

    @Override
    public void delete(Long id) {
        if (!restauranteRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el restaurante con id: " + id);
        }
        restauranteRepository.deleteById(id);
    }

    @Override
    public RestauranteDTO findById(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante no encontrado con id: " + id));
        return toDTO(restaurante);
    }

    @Override
    public List<RestauranteDTO> listAll() {
        return toDTOList(restauranteRepository.findAll());
    }

    // ----------------------------------------------------
    // Query Methods
    // ----------------------------------------------------

    @Override
    public List<RestauranteDTO> findByCiudad(String ciudad) {
        return toDTOList(restauranteRepository.findByCiudad(ciudad));
    }

    @Override
    public List<RestauranteDTO> findByAforoMesasGreaterThan(Integer aforo) {
        return toDTOList(restauranteRepository.findByAforoMesasGreaterThan(aforo));
    }

    @Override
    public List<RestauranteDTO> findByUsuarioId(Long usuarioId) {
        return toDTOList(restauranteRepository.findByUsuario_Id(usuarioId));
    }

    // ----------------------------------------------------
    // SQL Nativo
    // ----------------------------------------------------

    @Override
    public List<RestauranteDTO> findByCiudadSQL(String ciudad) {
        return toDTOList(restauranteRepository.findByCiudadSQL(ciudad));
    }

    // ----------------------------------------------------
    // JPQL
    // ----------------------------------------------------

    @Override
    public List<RestauranteDTO> findByCiudadJPQL(String ciudad) {
        return toDTOList(restauranteRepository.findByCiudadJPQL(ciudad));
    }
}