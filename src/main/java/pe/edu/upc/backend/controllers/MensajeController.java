package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.MensajeDTO;
import pe.edu.upc.backend.services.MensajeService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @GetMapping("/mensajes")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Tal vez solo admin ve todos
    public ResponseEntity<List<MensajeDTO>> listAll() {
        return new ResponseEntity<>(mensajeService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/mensajes/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARTISTA','ROLE_RESTAURANTE')")
    public ResponseEntity<MensajeDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(mensajeService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/mensajes")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA','ROLE_RESTAURANTE')")
    public ResponseEntity<MensajeDTO> add(@RequestBody MensajeDTO dto) {
        return new ResponseEntity<>(mensajeService.add(dto), HttpStatus.CREATED);
    }

    @PutMapping("/mensajes/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA','ROLE_RESTAURANTE')")
    public ResponseEntity<MensajeDTO> update(@PathVariable("id") Long id, @RequestBody MensajeDTO dto) {
        return new ResponseEntity<>(mensajeService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/mensajes/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        mensajeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/mensajes/anuncio/{id}")
    public ResponseEntity<List<MensajeDTO>> findByAnuncio(@PathVariable("id") Long id) {
        return new ResponseEntity<>(mensajeService.findByAnuncioId(id), HttpStatus.OK);
    }

    // ... agrega los otros endpoints SQL/JPQL con seguridad ADMIN si deseas
}