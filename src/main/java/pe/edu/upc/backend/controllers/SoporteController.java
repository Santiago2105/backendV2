package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.SoporteDTO;
import pe.edu.upc.backend.services.SoporteService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;

    @GetMapping("/soportes")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Solo admin ve todos los tickets
    public ResponseEntity<List<SoporteDTO>> listAll() {
        return new ResponseEntity<>(soporteService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/soportes/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARTISTA','ROLE_RESTAURANTE')")
    public ResponseEntity<SoporteDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(soporteService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/soportes")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA','ROLE_RESTAURANTE')") // Usuarios crean tickets
    public ResponseEntity<SoporteDTO> add(@RequestBody SoporteDTO dto) {
        return new ResponseEntity<>(soporteService.add(dto), HttpStatus.CREATED);
    }

    @PutMapping("/soportes/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Admin responde/cierra tickets
    public ResponseEntity<SoporteDTO> update(@PathVariable("id") Long id, @RequestBody SoporteDTO dto) {
        return new ResponseEntity<>(soporteService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/soportes/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        soporteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ... endpoints de búsqueda
}