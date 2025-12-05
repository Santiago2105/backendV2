package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.NotificacionDTO;
import pe.edu.upc.backend.services.NotificacionService;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/upc")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/notificaciones")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Solo admin ve todas
    public ResponseEntity<List<NotificacionDTO>> listAll() {
        return new ResponseEntity<>(notificacionService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/notificaciones/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_ARTISTA','ROLE_RESTAURANTE')")
    public ResponseEntity<NotificacionDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(notificacionService.findById(id), HttpStatus.OK);
    }

    // Crear notificación (generalmente el sistema lo hace, pero dejamos el endpoint para admin o pruebas)
    @PostMapping("/notificaciones")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<NotificacionDTO> add(@RequestBody NotificacionDTO dto) {
        return new ResponseEntity<>(notificacionService.add(dto), HttpStatus.CREATED);
    }

    // Editar (ej: marcar como leído)
    @PutMapping("/notificaciones/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA', 'ROLE_RESTAURANTE', 'ROLE_ADMIN')")
    public ResponseEntity<NotificacionDTO> update(@PathVariable("id") Long id, @RequestBody NotificacionDTO dto) {
        return new ResponseEntity<>(notificacionService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/notificaciones/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        notificacionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Buscar mis notificaciones (para artistas y restaurantes)
    @GetMapping("/notificaciones/usuario/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ARTISTA', 'ROLE_RESTAURANTE', 'ROLE_ADMIN')")
    public ResponseEntity<List<NotificacionDTO>> findByUsuario(@PathVariable("id") Long usuarioId) {
        return new ResponseEntity<>(notificacionService.findByUsuarioId(usuarioId), HttpStatus.OK);
    }

    // ... otros endpoints de consulta
}