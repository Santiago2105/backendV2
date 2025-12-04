package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.AnuncioDTO;
import pe.edu.upc.backend.services.AnuncioService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class AnuncioController {

    @Autowired
    private AnuncioService anuncioService;

    @GetMapping("/anuncios")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_RESTAURANTE')")
    public ResponseEntity<List<AnuncioDTO>> listAll() {
        return new ResponseEntity<>(anuncioService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/anuncios/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_RESTAURANTE')")
    public ResponseEntity<AnuncioDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(anuncioService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/anuncios")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_RESTAURANTE')") // Restaurante crea anuncio
    public ResponseEntity<AnuncioDTO> add(@RequestBody AnuncioDTO anuncioDTO) {
        AnuncioDTO created = anuncioService.add(anuncioDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/anuncios/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_RESTAURANTE')")
    public ResponseEntity<AnuncioDTO> update(@PathVariable("id") Long id, @RequestBody AnuncioDTO anuncioDTO) {
        AnuncioDTO updated = anuncioService.update(id, anuncioDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/anuncios/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN',ROLE_RESTAURANTE)")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        anuncioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Consultas
    @GetMapping("/anuncios/evento/{id}")
    public ResponseEntity<List<AnuncioDTO>> findByEvento(@PathVariable("id") Long eventoId) {
        return new ResponseEntity<>(anuncioService.findByEventoId(eventoId), HttpStatus.OK);
    }

    @GetMapping("/anuncios/activo/{activo}")
    public ResponseEntity<List<AnuncioDTO>> findByActivo(@PathVariable("activo") boolean activo) {
        return new ResponseEntity<>(anuncioService.findByActivo(activo), HttpStatus.OK);
    }

    @GetMapping("/anuncios/genero/{genero}")
    public ResponseEntity<List<AnuncioDTO>> findByGenero(@PathVariable("genero") String genero) {
        return new ResponseEntity<>(anuncioService.findByGeneroBuscado(genero), HttpStatus.OK);
    }
}