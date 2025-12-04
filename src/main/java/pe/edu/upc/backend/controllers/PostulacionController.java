package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.PostulacionDTO;
import pe.edu.upc.backend.services.PostulacionService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    @GetMapping("/postulaciones")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<PostulacionDTO>> listAll() {
        return new ResponseEntity<>(postulacionService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/postulaciones/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARTISTA','ROLE_RESTAURANTE')")
    public ResponseEntity<PostulacionDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postulacionService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/postulaciones")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA')") // Artistas postulan
    public ResponseEntity<PostulacionDTO> add(@RequestBody PostulacionDTO dto) {
        return new ResponseEntity<>(postulacionService.add(dto), HttpStatus.CREATED);
    }

    @PutMapping("/postulaciones/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANTE')") // Restaurantes aceptan (update)
    public ResponseEntity<PostulacionDTO> update(@PathVariable("id") Long id, @RequestBody PostulacionDTO dto) {
        return new ResponseEntity<>(postulacionService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/postulaciones/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        postulacionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Consultas
    @GetMapping("/postulaciones/anuncio/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANTE','ROLE_ADMIN')")
    public ResponseEntity<List<PostulacionDTO>> findByAnuncio(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postulacionService.findByAnuncioId(id), HttpStatus.OK);
    }

    // ... otros endpoints de consulta
}