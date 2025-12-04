package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Soporte;
import pe.edu.upc.backend.services.SoporteService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;

    // ---------------------- CRUD ----------------------

    // Listar todos
    @GetMapping("/soportes")
    public ResponseEntity<List<Soporte>> listAll() {
        return new ResponseEntity<>(soporteService.listAll(), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/soportes/{id}")
    public ResponseEntity<Soporte> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(soporteService.findById(id), HttpStatus.OK);
    }

    // Crear ticket de soporte
    @PostMapping("/soportes")
    public ResponseEntity<Soporte> add(@RequestBody Soporte soporte) {
        Soporte created = soporteService.add(soporte);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Editar ticket de soporte
    @PutMapping("/soportes")
    public ResponseEntity<Soporte> edit(@RequestBody Soporte soporte) {
        Soporte updated = soporteService.edit(soporte);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar ticket de soporte
    @DeleteMapping("/soportes/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        soporteService.delete(id);
        return new ResponseEntity<>("Soporte eliminado correctamente", HttpStatus.OK);
    }

    // ---------------------- QUERY METHODS ----------------------

    // Buscar tickets por estado (true = abierto/cerrado según tu modelo)
    @GetMapping("/soportes/estado/{estado}")
    public ResponseEntity<List<Soporte>> findByEstado(@PathVariable("estado") boolean estado) {
        return new ResponseEntity<>(soporteService.findByEstado(estado), HttpStatus.OK);
    }

    // Buscar tickets por usuario
    @GetMapping("/soportes/usuario/{id}")
    public ResponseEntity<List<Soporte>> findByUsuario(@PathVariable("id") Long usuarioId) {
        return new ResponseEntity<>(soporteService.findByUsuarioId(usuarioId), HttpStatus.OK);
    }

    // ---------------------- SQL NATIVO ----------------------

    // Buscar tickets por estado (SQL)
    @GetMapping("/soportes/sql/estado/{estado}")
    public ResponseEntity<List<Soporte>> findByEstadoSQL(@PathVariable("estado") boolean estado) {
        return new ResponseEntity<>(soporteService.findByEstadoSQL(estado), HttpStatus.OK);
    }

    // ---------------------- JPQL ----------------------

    // Buscar tickets por estado (JPQL)
    @GetMapping("/soportes/jpql/estado/{estado}")
    public ResponseEntity<List<Soporte>> findByEstadoJPQL(@PathVariable("estado") boolean estado) {
        return new ResponseEntity<>(soporteService.findByEstadoJPQL(estado), HttpStatus.OK);
    }
}
