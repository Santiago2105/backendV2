package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Portafolio;
import pe.edu.upc.backend.services.PortafolioService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class PortafolioController {

    @Autowired
    private PortafolioService portafolioService;

    // ---------------------- CRUD ----------------------

    // Listar todos
    @GetMapping("/portafolios")
    public ResponseEntity<List<Portafolio>> listAll() {
        return new ResponseEntity<>(portafolioService.listAll(), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/portafolios/{id}")
    public ResponseEntity<Portafolio> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(portafolioService.findById(id), HttpStatus.OK);
    }

    // Crear portafolio
    @PostMapping("/portafolios")
    public ResponseEntity<Portafolio> add(@RequestBody Portafolio portafolio) {
        Portafolio created = portafolioService.add(portafolio);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Editar portafolio
    @PutMapping("/portafolios")
    public ResponseEntity<Portafolio> edit(@RequestBody Portafolio portafolio) {
        Portafolio updated = portafolioService.edit(portafolio);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar portafolio
    @DeleteMapping("/portafolios/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        portafolioService.delete(id);
        return new ResponseEntity<>("Portafolio eliminado correctamente", HttpStatus.OK);
    }

    // ---------------------- QUERY METHODS ----------------------

    // Buscar por artista
    @GetMapping("/portafolios/artista/{id}")
    public ResponseEntity<List<Portafolio>> findByArtista(@PathVariable("id") Long artistaId) {
        return new ResponseEntity<>(portafolioService.findByArtistaId(artistaId), HttpStatus.OK);
    }

    // Buscar por tipo (audio, video, redes, etc.)
    @GetMapping("/portafolios/tipo/{tipo}")
    public ResponseEntity<List<Portafolio>> findByTipo(@PathVariable("tipo") String tipo) {
        return new ResponseEntity<>(portafolioService.findByTipo(tipo), HttpStatus.OK);
    }

    // Buscar por título
    @GetMapping("/portafolios/titulo/{titulo}")
    public ResponseEntity<List<Portafolio>> findByTitulo(@PathVariable("titulo") String titulo) {
        return new ResponseEntity<>(portafolioService.findByTitulo(titulo), HttpStatus.OK);
    }

    // ---------------------- SQL NATIVO ----------------------

    // Buscar por artista (SQL)
    @GetMapping("/portafolios/sql/artista/{id}")
    public ResponseEntity<List<Portafolio>> findByArtistaSQL(@PathVariable("id") Long artistaId) {
        return new ResponseEntity<>(portafolioService.findByArtistaSQL(artistaId), HttpStatus.OK);
    }

    // ---------------------- JPQL ----------------------

    // Buscar por artista (JPQL)
    @GetMapping("/portafolios/jpql/artista/{id}")
    public ResponseEntity<List<Portafolio>> findByArtistaJPQL(@PathVariable("id") Long artistaId) {
        return new ResponseEntity<>(portafolioService.findByArtistaJPQL(artistaId), HttpStatus.OK);
    }
}
