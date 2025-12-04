package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.ArtistaDTO;
import pe.edu.upc.backend.services.ArtistaService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    // -------------------------------------------------------
    // CRUD (PROTEGIDOS)
    // -------------------------------------------------------

    @GetMapping("/artistas")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<List<ArtistaDTO>> listAll() {
        return new ResponseEntity<>(artistaService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/artistas/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<ArtistaDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(artistaService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/artistas")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<ArtistaDTO> add(@RequestBody ArtistaDTO dto) {
        ArtistaDTO created = artistaService.add(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/artistas/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<ArtistaDTO> update(@PathVariable("id") Long id,
                                             @RequestBody ArtistaDTO dto) {
        ArtistaDTO updated = artistaService.update(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/artistas/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA', 'ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        artistaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // -------------------------------------------------------
    // CONSULTAS (PÚBLICAS)
    // -------------------------------------------------------

    @GetMapping("/public/artistas/genero/{genero}")
    public ResponseEntity<List<ArtistaDTO>> findByGenero(@PathVariable("genero") String genero) {
        return new ResponseEntity<>(artistaService.findByGeneroPrincipal(genero), HttpStatus.OK);
    }

    @GetMapping("/public/artistas/ciudad/{ciudad}")
    public ResponseEntity<List<ArtistaDTO>> findByCiudad(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(artistaService.findByCiudad(ciudad), HttpStatus.OK);
    }

    @GetMapping("/public/artistas/usuario/{usuarioId}")
    public ResponseEntity<List<ArtistaDTO>> findByUsuario(@PathVariable("usuarioId") Long usuarioId) {
        return new ResponseEntity<>(artistaService.findByUsuarioId(usuarioId), HttpStatus.OK);
    }

    // -------------------------------------------------------
    // SQL NATIVO / JPQL (solo ADMIN)
    // -------------------------------------------------------

    @GetMapping("/admin/artistas/sql/ciudad/{ciudad}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ArtistaDTO>> findByCiudadSQL(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(artistaService.findByCiudadSQL(ciudad), HttpStatus.OK);
    }

    @GetMapping("/admin/artistas/jpql/ciudad/{ciudad}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ArtistaDTO>> findByCiudadJPQL(@PathVariable("ciudad") String ciudad) {
        return new ResponseEntity<>(artistaService.findByCiudadJPQL(ciudad), HttpStatus.OK);
    }
}
