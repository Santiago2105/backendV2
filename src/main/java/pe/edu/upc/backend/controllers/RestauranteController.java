package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.RestauranteDTO;
import pe.edu.upc.backend.services.RestauranteService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    // -------------------------------------------------------
    // CRUD (PROTEGIDOS)
    // -------------------------------------------------------

    @GetMapping("/restaurantes")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANTE', 'ROLE_ADMIN')")
    public ResponseEntity<List<RestauranteDTO>> listAll() {
        return new ResponseEntity<>(restauranteService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/restaurantes/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANTE', 'ROLE_ADMIN')")
    public ResponseEntity<RestauranteDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(restauranteService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/restaurantes")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANTE', 'ROLE_ADMIN')")
    public ResponseEntity<RestauranteDTO> add(@RequestBody RestauranteDTO dto) {
        RestauranteDTO created = restauranteService.add(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/restaurantes/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANTE', 'ROLE_ADMIN')")
    public ResponseEntity<RestauranteDTO> update(@PathVariable("id") Long id,
                                                 @RequestBody RestauranteDTO dto) {
        RestauranteDTO updated = restauranteService.update(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/restaurantes/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Solo Admin suele borrar
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        restauranteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // -------------------------------------------------------
    // CONSULTAS (PÚBLICAS o SEMI-PÚBLICAS)
    // -------------------------------------------------------

    @GetMapping("/public/restaurantes/ciudad/{ciudad}")
    public ResponseEntity<List<RestauranteDTO>> findByCiudad(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(restauranteService.findByCiudad(ciudad), HttpStatus.OK);
    }

    @GetMapping("/public/restaurantes/usuario/{usuarioId}")
    public ResponseEntity<List<RestauranteDTO>> findByUsuario(@PathVariable("usuarioId") Long usuarioId) {
        return new ResponseEntity<>(restauranteService.findByUsuarioId(usuarioId), HttpStatus.OK);
    }

    // -------------------------------------------------------
    // SQL NATIVO / JPQL (ADMIN)
    // -------------------------------------------------------

    @GetMapping("/admin/restaurantes/sql/ciudad/{ciudad}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<RestauranteDTO>> findByCiudadSQL(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(restauranteService.findByCiudadSQL(ciudad), HttpStatus.OK);
    }

    @GetMapping("/admin/restaurantes/jpql/ciudad/{ciudad}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<RestauranteDTO>> findByCiudadJPQL(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(restauranteService.findByCiudadJPQL(ciudad), HttpStatus.OK);
    }
}