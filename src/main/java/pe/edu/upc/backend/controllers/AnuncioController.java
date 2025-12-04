package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Anuncio;
import pe.edu.upc.backend.services.AnuncioService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class AnuncioController {

    @Autowired
    private AnuncioService anuncioService;

    // ---------------------- CRUD ----------------------

    // Listar todos
    @GetMapping("/anuncios")
    public ResponseEntity<List<Anuncio>> listAll() {
        return new ResponseEntity<>(anuncioService.listAll(), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/anuncios/{id}")
    public ResponseEntity<Anuncio> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(anuncioService.findById(id), HttpStatus.OK);
    }

    // Crear anuncio
    @PostMapping("/anuncios")
    public ResponseEntity<Anuncio> add(@RequestBody Anuncio anuncio) {
        Anuncio created = anuncioService.add(anuncio);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Editar anuncio
    @PutMapping("/anuncios")
    public ResponseEntity<Anuncio> edit(@RequestBody Anuncio anuncio) {
        Anuncio updated = anuncioService.edit(anuncio);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar anuncio
    @DeleteMapping("/anuncios/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        anuncioService.delete(id);
        return new ResponseEntity<>("Anuncio eliminado correctamente", HttpStatus.OK);
    }

    // ---------------------- QUERY METHODS ----------------------

    // Buscar anuncios por evento
    @GetMapping("/anuncios/evento/{id}")
    public ResponseEntity<List<Anuncio>> findByEvento(@PathVariable("id") Long eventoId) {
        return new ResponseEntity<>(anuncioService.findByEventoId(eventoId), HttpStatus.OK);
    }

    // Buscar anuncios por estado (activo / inactivo)
    @GetMapping("/anuncios/activo/{activo}")
    public ResponseEntity<List<Anuncio>> findByActivo(@PathVariable("activo") boolean activo) {
        return new ResponseEntity<>(anuncioService.findByActivo(activo), HttpStatus.OK);
    }

    // Buscar anuncios por género buscado
    @GetMapping("/anuncios/genero/{genero}")
    public ResponseEntity<List<Anuncio>> findByGenero(@PathVariable("genero") String genero) {
        return new ResponseEntity<>(anuncioService.findByGeneroBuscado(genero), HttpStatus.OK);
    }

    // ---------------------- SQL NATIVO ----------------------

    // Buscar anuncios por evento (SQL)
    @GetMapping("/anuncios/sql/evento/{id}")
    public ResponseEntity<List<Anuncio>> findByEventoSQL(@PathVariable("id") Long eventoId) {
        return new ResponseEntity<>(anuncioService.findByEventoSQL(eventoId), HttpStatus.OK);
    }

    // ---------------------- JPQL ----------------------

    // Buscar anuncios por evento (JPQL)
    @GetMapping("/anuncios/jpql/evento/{id}")
    public ResponseEntity<List<Anuncio>> findByEventoJPQL(@PathVariable("id") Long eventoId) {
        return new ResponseEntity<>(anuncioService.findByEventoJPQL(eventoId), HttpStatus.OK);
    }
}
