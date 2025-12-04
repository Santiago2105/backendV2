package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Restaurante;
import pe.edu.upc.backend.services.RestauranteService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    // ---------------------- CRUD ----------------------

    // Listar todos
    @GetMapping("/restaurantes")
    public ResponseEntity<List<Restaurante>> listAll() {
        return new ResponseEntity<>(restauranteService.listAll(), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/restaurantes/{id}")
    public ResponseEntity<Restaurante> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(restauranteService.findById(id), HttpStatus.OK);
    }

    // Crear restaurante
    @PostMapping("/restaurantes")
    public ResponseEntity<Restaurante> add(@RequestBody Restaurante restaurante) {
        Restaurante created = restauranteService.add(restaurante);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Editar restaurante
    @PutMapping("/restaurantes")
    public ResponseEntity<Restaurante> edit(@RequestBody Restaurante restaurante) {
        Restaurante updated = restauranteService.edit(restaurante);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar restaurante
    @DeleteMapping("/restaurantes/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        restauranteService.delete(id);
        return new ResponseEntity<>("Restaurante eliminado correctamente", HttpStatus.OK);
    }

    // ---------------------- QUERY METHODS ----------------------

    // Buscar por ciudad
    @GetMapping("/restaurantes/ciudad/{ciudad}")
    public ResponseEntity<List<Restaurante>> findByCiudad(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(restauranteService.findByCiudad(ciudad), HttpStatus.OK);
    }

    // Buscar por usuario
    @GetMapping("/restaurantes/usuario/{id}")
    public ResponseEntity<List<Restaurante>> findByUsuario(@PathVariable("id") Long id) {
        return new ResponseEntity<>(restauranteService.findByUsuarioId(id), HttpStatus.OK);
    }

    // ---------------------- SQL NATIVO ----------------------

    // Buscar por ciudad (SQL)
    @GetMapping("/restaurantes/sql/ciudad/{ciudad}")
    public ResponseEntity<List<Restaurante>> findByCiudadSQL(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(restauranteService.findByCiudadSQL(ciudad), HttpStatus.OK);
    }

    // ---------------------- JPQL ----------------------

    // Buscar por ciudad (JPQL)
    @GetMapping("/restaurantes/jpql/ciudad/{ciudad}")
    public ResponseEntity<List<Restaurante>> findByCiudadJPQL(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(restauranteService.findByCiudadJPQL(ciudad), HttpStatus.OK);
    }
}
