package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.EventoDTO;
import pe.edu.upc.backend.services.EventoService;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // ================================
    // CRUD
    // ================================

    @GetMapping("/eventos")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_RESTAURANTE')")
    public ResponseEntity<List<EventoDTO>> listAll() {
        return new ResponseEntity<>(eventoService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/eventos/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_RESTAURANTE')")
    public ResponseEntity<EventoDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(eventoService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/eventos")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_RESTAURANTE')")
    public ResponseEntity<EventoDTO> add(@RequestBody EventoDTO eventoDTO) {
        EventoDTO created = eventoService.add(eventoDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/eventos/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_RESTAURANTE')")
    public ResponseEntity<EventoDTO> update(@PathVariable("id") Long id, @RequestBody EventoDTO eventoDTO) {
        EventoDTO updated = eventoService.update(id, eventoDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/eventos/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_RESTAURANTE')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        eventoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ================================
    // CONSULTAS ESPECÍFICAS
    // ================================

    @GetMapping("/eventos/restaurante/{id}")
    public ResponseEntity<List<EventoDTO>> findByRestaurante(@PathVariable("id") Long restauranteId) {
        return new ResponseEntity<>(eventoService.findByRestauranteId(restauranteId), HttpStatus.OK);
    }

    @GetMapping("/eventos/fecha/{fecha}")
    public ResponseEntity<List<EventoDTO>> findByFecha(@PathVariable("fecha") String fecha) {
        LocalDate date = LocalDate.parse(fecha);
        return new ResponseEntity<>(eventoService.findByFechaEvento(date), HttpStatus.OK);
    }
}
