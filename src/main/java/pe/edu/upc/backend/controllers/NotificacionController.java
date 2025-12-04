package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Notificacion;
import pe.edu.upc.backend.services.NotificacionService;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // ---------------------- CRUD ----------------------

    // Listar todas
    @GetMapping("/notificaciones")
    public ResponseEntity<List<Notificacion>> listAll() {
        return new ResponseEntity<>(notificacionService.listAll(), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/notificaciones/{id}")
    public ResponseEntity<Notificacion> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(notificacionService.findById(id), HttpStatus.OK);
    }

    // Crear notificación
    @PostMapping("/notificaciones")
    public ResponseEntity<Notificacion> add(@RequestBody Notificacion notificacion) {
        Notificacion created = notificacionService.add(notificacion);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Editar notificación
    @PutMapping("/notificaciones")
    public ResponseEntity<Notificacion> edit(@RequestBody Notificacion notificacion) {
        Notificacion updated = notificacionService.edit(notificacion);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar notificación
    @DeleteMapping("/notificaciones/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        notificacionService.delete(id);
        return new ResponseEntity<>("Notificación eliminada correctamente", HttpStatus.OK);
    }

    // ---------------------- QUERY METHODS ----------------------

    // Buscar por usuario
    @GetMapping("/notificaciones/usuario/{id}")
    public ResponseEntity<List<Notificacion>> findByUsuario(@PathVariable("id") Long usuarioId) {
        return new ResponseEntity<>(notificacionService.findByUsuarioId(usuarioId), HttpStatus.OK);
    }

    // Buscar por estado leído / no leído
    @GetMapping("/notificaciones/leido/{leido}")
    public ResponseEntity<List<Notificacion>> findByLeido(@PathVariable("leido") boolean leido) {
        return new ResponseEntity<>(notificacionService.findByLeido(leido), HttpStatus.OK);
    }

    // Buscar por rango de fechas (yyyy-MM-dd)
    @GetMapping("/notificaciones/fecha/{inicio}/{fin}")
    public ResponseEntity<List<Notificacion>> findByFechaBetween(
            @PathVariable("inicio") String inicio,
            @PathVariable("fin") String fin
    ) {
        LocalDate fechaInicio = LocalDate.parse(inicio);
        LocalDate fechaFin = LocalDate.parse(fin);
        return new ResponseEntity<>(notificacionService.findByFechaNotificacionBetween(fechaInicio, fechaFin), HttpStatus.OK);
    }

    // ---------------------- SQL NATIVO ----------------------

    // Buscar notificaciones por usuario (SQL)
    @GetMapping("/notificaciones/sql/usuario/{id}")
    public ResponseEntity<List<Notificacion>> findByUsuarioSQL(@PathVariable("id") Long usuarioId) {
        return new ResponseEntity<>(notificacionService.findByUsuarioSQL(usuarioId), HttpStatus.OK);
    }

    // ---------------------- JPQL ----------------------

    // Buscar notificaciones por usuario (JPQL)
    @GetMapping("/notificaciones/jpql/usuario/{id}")
    public ResponseEntity<List<Notificacion>> findByUsuarioJPQL(@PathVariable("id") Long usuarioId) {
        return new ResponseEntity<>(notificacionService.findByUsuarioJPQL(usuarioId), HttpStatus.OK);
    }
}
