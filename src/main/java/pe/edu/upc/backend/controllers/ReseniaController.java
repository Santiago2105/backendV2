package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.ReseniaDTO;
import pe.edu.upc.backend.services.ReseniaService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;

    @GetMapping("/resenias")
    public ResponseEntity<List<ReseniaDTO>> listAll() {
        return new ResponseEntity<>(reseniaService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/resenias/{id}")
    public ResponseEntity<ReseniaDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(reseniaService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/resenias")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA', 'ROLE_RESTAURANTE')")
    public ResponseEntity<ReseniaDTO> add(@RequestBody ReseniaDTO dto) {
        return new ResponseEntity<>(reseniaService.add(dto), HttpStatus.CREATED);
    }

    @PutMapping("/resenias/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Solo admin edita reseñas por seguridad
    public ResponseEntity<ReseniaDTO> update(@PathVariable("id") Long id, @RequestBody ReseniaDTO dto) {
        return new ResponseEntity<>(reseniaService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/resenias/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reseniaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/resenias/evento/{id}")
    public ResponseEntity<List<ReseniaDTO>> findByEvento(@PathVariable("id") Long id) {
        return new ResponseEntity<>(reseniaService.findByEventoId(id), HttpStatus.OK);
    }
    // ... otros endpoints de búsqueda
}