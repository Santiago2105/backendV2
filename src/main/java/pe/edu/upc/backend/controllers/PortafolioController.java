package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.PortafolioDTO;
import pe.edu.upc.backend.services.PortafolioService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class PortafolioController {

    @Autowired
    private PortafolioService portafolioService;

    @GetMapping("/portafolios")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARTISTA','ROLE_RESTAURANTE')")
    public ResponseEntity<List<PortafolioDTO>> listAll() {
        return new ResponseEntity<>(portafolioService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/portafolios/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARTISTA','ROLE_RESTAURANTE')")
    public ResponseEntity<PortafolioDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(portafolioService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/portafolios")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA')") // Solo artistas suben portafolio
    public ResponseEntity<PortafolioDTO> add(@RequestBody PortafolioDTO dto) {
        return new ResponseEntity<>(portafolioService.add(dto), HttpStatus.CREATED);
    }

    @PutMapping("/portafolios/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA')")
    public ResponseEntity<PortafolioDTO> update(@PathVariable("id") Long id, @RequestBody PortafolioDTO dto) {
        return new ResponseEntity<>(portafolioService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/portafolios/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        portafolioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Consultas
    @GetMapping("/portafolios/artista/{id}")
    public ResponseEntity<List<PortafolioDTO>> findByArtista(@PathVariable("id") Long artistaId) {
        return new ResponseEntity<>(portafolioService.findByArtistaId(artistaId), HttpStatus.OK);
    }

    // ... otros endpoints de búsqueda
}