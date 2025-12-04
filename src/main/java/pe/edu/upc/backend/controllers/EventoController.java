package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Evento;
import pe.edu.upc.backend.services.EventoService;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // ---------------------- CRUD ----------------------

    // Listar todos
    @GetMapping("/eventos")
    public ResponseEntity<List<Evento>> listAll() {
        return new ResponseEntity<>(eventoService.listAll(), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/eventos/{id}")
    public ResponseEntity<Evento> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(eventoService.findById(id), HttpStatus.OK);
    }

    // Crear evento
    @PostMapping("/eventos")
    public ResponseEntity<Evento> add(@RequestBody Evento evento) {
        Evento created = eventoService.add(evento);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Editar evento
    @PutMapping("/eventos")
    public ResponseEntity<Evento> edit(@RequestBody Evento evento) {
        Evento updated = eventoService.edit(evento);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar evento
    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        eventoService.delete(id);
        return new ResponseEntity<>("Evento eliminado correctamente", HttpStatus.OK);
    }

    // ---------------------- QUERY METHODS ----------------------

    // Buscar por restaurante
    @GetMapping("/eventos/restaurante/{id}")
    public ResponseEntity<List<Evento>> findByRestaurante(@PathVariable("id") Long restauranteId) {
        return new ResponseEntity<>(eventoService.findByRestauranteId(restauranteId), HttpStatus.OK);
    }

    // Buscar por artista
    @GetMapping("/eventos/artista/{id}")
    public ResponseEntity<List<Evento>> findByArtista(@PathVariable("id") Long artistaId) {
        return new ResponseEntity<>(eventoService.findByArtistaId(artistaId), HttpStatus.OK);
    }

    // Buscar por fecha de evento (yyyy-MM-dd)
    @GetMapping("/eventos/fecha/{fecha}")
    public ResponseEntity<List<Evento>> findByFecha(@PathVariable("fecha") String fecha) {
        LocalDate fechaEvento = LocalDate.parse(fecha);
        return new ResponseEntity<>(eventoService.findByFechaEvento(fechaEvento), HttpStatus.OK);
    }

    // ---------------------- SQL NATIVO ----------------------

    // Buscar por restaurante (SQL)
    @GetMapping("/eventos/sql/restaurante/{id}")
    public ResponseEntity<List<Evento>> findByRestauranteSQL(@PathVariable("id") Long restauranteId) {
        return new ResponseEntity<>(eventoService.findByRestauranteSQL(restauranteId), HttpStatus.OK);
    }

    // ---------------------- JPQL ----------------------

    // Buscar por restaurante (JPQL)
    @GetMapping("/eventos/jpql/restaurante/{id}")
    public ResponseEntity<List<Evento>> findByRestauranteJPQL(@PathVariable("id") Long restauranteId) {
        return new ResponseEntity<>(eventoService.findByRestauranteJPQL(restauranteId), HttpStatus.OK);
    }
}
