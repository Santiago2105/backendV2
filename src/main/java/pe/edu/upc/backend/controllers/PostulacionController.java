package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Postulacion;
import pe.edu.upc.backend.services.PostulacionService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    // ---------------------- CRUD ----------------------

    // Listar todas
    @GetMapping("/postulaciones")
    public ResponseEntity<List<Postulacion>> listAll() {
        return new ResponseEntity<>(postulacionService.listAll(), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/postulaciones/{id}")
    public ResponseEntity<Postulacion> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postulacionService.findById(id), HttpStatus.OK);
    }

    // Crear postulación
    @PostMapping("/postulaciones")
    public ResponseEntity<Postulacion> add(@RequestBody Postulacion postulacion) {
        Postulacion created = postulacionService.add(postulacion);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Editar postulación
    @PutMapping("/postulaciones")
    public ResponseEntity<Postulacion> edit(@RequestBody Postulacion postulacion) {
        Postulacion updated = postulacionService.edit(postulacion);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar postulación
    @DeleteMapping("/postulaciones/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        postulacionService.delete(id);
        return new ResponseEntity<>("Postulación eliminada correctamente", HttpStatus.OK);
    }

    // ---------------------- QUERY METHODS ----------------------

    // Buscar postulaciones por anuncio
    @GetMapping("/postulaciones/anuncio/{id}")
    public ResponseEntity<List<Postulacion>> findByAnuncio(@PathVariable("id") Long anuncioId) {
        return new ResponseEntity<>(postulacionService.findByAnuncioId(anuncioId), HttpStatus.OK);
    }

    // Buscar postulaciones por artista
    @GetMapping("/postulaciones/artista/{id}")
    public ResponseEntity<List<Postulacion>> findByArtista(@PathVariable("id") Long artistaId) {
        return new ResponseEntity<>(postulacionService.findByArtistaId(artistaId), HttpStatus.OK);
    }

    // Buscar postulaciones por estado (aceptada / no aceptada)
    @GetMapping("/postulaciones/aceptada/{aceptada}")
    public ResponseEntity<List<Postulacion>> findByAceptada(@PathVariable("aceptada") boolean aceptada) {
        return new ResponseEntity<>(postulacionService.findByAceptada(aceptada), HttpStatus.OK);
    }

    // ---------------------- SQL NATIVO ----------------------

    // Buscar postulaciones por anuncio (SQL)
    @GetMapping("/postulaciones/sql/anuncio/{id}")
    public ResponseEntity<List<Postulacion>> findByAnuncioSQL(@PathVariable("id") Long anuncioId) {
        return new ResponseEntity<>(postulacionService.findByAnuncioSQL(anuncioId), HttpStatus.OK);
    }

    // ---------------------- JPQL ----------------------

    // Buscar postulaciones por anuncio (JPQL)
    @GetMapping("/postulaciones/jpql/anuncio/{id}")
    public ResponseEntity<List<Postulacion>> findByAnuncioJPQL(@PathVariable("id") Long anuncioId) {
        return new ResponseEntity<>(postulacionService.findByAnuncioJPQL(anuncioId), HttpStatus.OK);
    }
}
