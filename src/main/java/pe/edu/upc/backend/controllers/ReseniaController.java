package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Resenia;
import pe.edu.upc.backend.services.ReseniaService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;

    // ---------------------- CRUD ----------------------

    // Listar todas
    @GetMapping("/resenias")
    public ResponseEntity<List<Resenia>> listAll() {
        return new ResponseEntity<>(reseniaService.listAll(), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/resenias/{id}")
    public ResponseEntity<Resenia> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(reseniaService.findById(id), HttpStatus.OK);
    }

    // Crear reseña
    @PostMapping("/resenias")
    public ResponseEntity<Resenia> add(@RequestBody Resenia resenia) {
        Resenia created = reseniaService.add(resenia);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Editar reseña
    @PutMapping("/resenias")
    public ResponseEntity<Resenia> edit(@RequestBody Resenia resenia) {
        Resenia updated = reseniaService.edit(resenia);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar reseña
    @DeleteMapping("/resenias/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        reseniaService.delete(id);
        return new ResponseEntity<>("Reseña eliminada correctamente", HttpStatus.OK);
    }

    // ---------------------- QUERY METHODS ----------------------

    // Buscar reseñas por evento
    @GetMapping("/resenias/evento/{id}")
    public ResponseEntity<List<Resenia>> findByEvento(@PathVariable("id") Long eventoId) {
        return new ResponseEntity<>(reseniaService.findByEventoId(eventoId), HttpStatus.OK);
    }

    // Buscar reseñas por artista
    @GetMapping("/resenias/artista/{id}")
    public ResponseEntity<List<Resenia>> findByArtista(@PathVariable("id") Long artistaId) {
        return new ResponseEntity<>(reseniaService.findByArtistaId(artistaId), HttpStatus.OK);
    }

    // Buscar reseñas por restaurante
    @GetMapping("/resenias/restaurante/{id}")
    public ResponseEntity<List<Resenia>> findByRestaurante(@PathVariable("id") Long restauranteId) {
        return new ResponseEntity<>(reseniaService.findByRestauranteId(restauranteId), HttpStatus.OK);
    }

    // ---------------------- SQL NATIVO ----------------------

    // Buscar reseñas por evento (SQL)
    @GetMapping("/resenias/sql/evento/{id}")
    public ResponseEntity<List<Resenia>> findByEventoSQL(@PathVariable("id") Long eventoId) {
        return new ResponseEntity<>(reseniaService.findByEventoSQL(eventoId), HttpStatus.OK);
    }

    // ---------------------- JPQL ----------------------

    // Buscar reseñas por evento (JPQL)
    @GetMapping("/resenias/jpql/evento/{id}")
    public ResponseEntity<List<Resenia>> findByEventoJPQL(@PathVariable("id") Long eventoId) {
        return new ResponseEntity<>(reseniaService.findByEventoJPQL(eventoId), HttpStatus.OK);
    }
}
