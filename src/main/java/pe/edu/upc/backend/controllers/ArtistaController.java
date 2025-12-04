package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.ArtistaDTO;
import pe.edu.upc.backend.entities.Artista;
import pe.edu.upc.backend.services.ArtistaService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    // ---------------------- CRUD ----------------------

    // Listar todos
    @GetMapping("/artistas")
    @PreAuthorize("hasAnyRole('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<List<Artista>> listAll() {
        return new ResponseEntity<>(artistaService.listAll(), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/artistas/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Artista> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(artistaService.findById(id), HttpStatus.OK);
    }

    // Crear artista
    @PostMapping("/artistas")
    @PreAuthorize("hasAnyRole('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<ArtistaDTO> add(@RequestBody ArtistaDTO artista) {
        System.out.println("****************************************");
        System.out.println("Artista: "+ artista.getId().toString());
        System.out.println("Artista: "+ artista.getNombreArtistico());
        System.out.println("Artista: "+ artista.getBio());
        System.out.println("Artista: "+ artista.getCiudad());
        System.out.println("Artista: "+ artista.getGeneroPrincipal());
        ArtistaDTO created = artistaService.add(artista);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Editar artista
    @PutMapping("/artistas")
    @PreAuthorize("hasAnyRole('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<Artista> edit(@RequestBody Artista artista) {
        Artista updated = artistaService.edit(artista);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar artista
    @DeleteMapping("/artistas/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        artistaService.delete(id);
        return new ResponseEntity<>("Artista eliminado correctamente", HttpStatus.OK);
    }

    // ---------------------- QUERY METHODS ----------------------

    // Buscar por género musical
    @GetMapping("/artistas/genero/{genero}")
    @PreAuthorize("hasAnyRole('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<List<Artista>> findByGenero(@PathVariable("genero") String genero) {
        return new ResponseEntity<>(artistaService.findByGeneroPrincipal(genero), HttpStatus.OK);
    }

    // Buscar por ciudad (Query Method)
    @GetMapping("/artistas/ciudad/{ciudad}")
    @PreAuthorize("hasAnyRole('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<List<Artista>> findByCiudad(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(artistaService.findByCiudad(ciudad), HttpStatus.OK);
    }

    // Buscar artistas por usuario
    @GetMapping("/artistas/usuario/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<Artista>> findByUsuario(@PathVariable("id") Long id) {
        return new ResponseEntity<>(artistaService.findByUsuarioId(id), HttpStatus.OK);
    }

    // ---------------------- SQL NATIVO ----------------------

    // Buscar por ciudad (SQL)
    @GetMapping("/artistas/sql/ciudad/{ciudad}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<Artista>> findByCiudadSQL(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(artistaService.findByCiudadSQL(ciudad), HttpStatus.OK);
    }

    // ---------------------- JPQL ----------------------

    // Buscar por ciudad (JPQL)
    @GetMapping("/artistas/jpql/ciudad/{ciudad}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<Artista>> findByCiudadJPQL(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(artistaService.findByCiudadJPQL(ciudad), HttpStatus.OK);
    }
}
