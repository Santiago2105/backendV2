package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Mensaje;
import pe.edu.upc.backend.services.MensajeService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    // ---------------------- CRUD ----------------------

    // Listar todos
    @GetMapping("/mensajes")
    public ResponseEntity<List<Mensaje>> listAll() {
        return new ResponseEntity<>(mensajeService.listAll(), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/mensajes/{id}")
    public ResponseEntity<Mensaje> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(mensajeService.findById(id), HttpStatus.OK);
    }

    // Crear mensaje
    @PostMapping("/mensajes")
    public ResponseEntity<Mensaje> add(@RequestBody Mensaje mensaje) {
        Mensaje created = mensajeService.add(mensaje);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Editar mensaje
    @PutMapping("/mensajes")
    public ResponseEntity<Mensaje> edit(@RequestBody Mensaje mensaje) {
        Mensaje updated = mensajeService.edit(mensaje);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar mensaje
    @DeleteMapping("/mensajes/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        mensajeService.delete(id);
        return new ResponseEntity<>("Mensaje eliminado correctamente", HttpStatus.OK);
    }

    // ---------------------- QUERY METHODS ----------------------

    // Buscar mensajes por anuncio
    @GetMapping("/mensajes/anuncio/{id}")
    public ResponseEntity<List<Mensaje>> findByAnuncio(@PathVariable("id") Long anuncioId) {
        return new ResponseEntity<>(mensajeService.findByAnuncioId(anuncioId), HttpStatus.OK);
    }

    // Buscar mensajes por usuario
    @GetMapping("/mensajes/usuario/{id}")
    public ResponseEntity<List<Mensaje>> findByUsuario(@PathVariable("id") Long usuarioId) {
        return new ResponseEntity<>(mensajeService.findByUsuarioId(usuarioId), HttpStatus.OK);
    }

    // ---------------------- SQL NATIVO ----------------------

    // Buscar mensajes por anuncio (SQL)
    @GetMapping("/mensajes/sql/anuncio/{id}")
    public ResponseEntity<List<Mensaje>> findByAnuncioSQL(@PathVariable("id") Long anuncioId) {
        return new ResponseEntity<>(mensajeService.findByAnuncioSQL(anuncioId), HttpStatus.OK);
    }

    // ---------------------- JPQL ----------------------

    // Buscar mensajes por anuncio (JPQL)
    @GetMapping("/mensajes/jpql/anuncio/{id}")
    public ResponseEntity<List<Mensaje>> findByAnuncioJPQL(@PathVariable("id") Long anuncioId) {
        return new ResponseEntity<>(mensajeService.findByAnuncioJPQL(anuncioId), HttpStatus.OK);
    }
}
