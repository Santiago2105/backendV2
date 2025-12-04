package pe.edu.upc.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pe.edu.upc.backend.dtos.DTOUser;
import pe.edu.upc.backend.entities.*;
import pe.edu.upc.backend.repositories.*;
import pe.edu.upc.backend.services.AuthorityService;
import pe.edu.upc.backend.services.UserService;

import java.time.LocalDate;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner startConfiguration(
            UserService userService,
            AuthorityService authorityService,

            ArtistaRepository artistaRepository,
            RestauranteRepository restauranteRepository,
            EventoRepository eventoRepository,
            AnuncioRepository anuncioRepository,
            MensajeRepository mensajeRepository,
            PostulacionRepository postulacionRepository,
            ReseniaRepository reseniaRepository,
            SoporteRepository soporteRepository,
            NotificacionRepository notificacionRepository,
            PortafolioRepository portafolioRepository
    ){
        return args -> {

            // ----------------------------------------------------
            // ROLES Y USUARIOS
            // ----------------------------------------------------

            // Solo dos roles: ADMIN y USER
            Authority roleAdmin = authorityService.add(new Authority(null, "ROLE_ADMIN", null));
            Authority roleArtista  = authorityService.add(new Authority(null, "ROLE_ARTISTA", null));
            Authority roleRestaurante  = authorityService.add(new Authority(null, "ROLE_RESTAURANTE", null));

            // Usuarios de prueba
            // admin -> ROLE_ADMIN;ROLE_USER
            userService.add(new DTOUser(null, "admin", "admin123", "ROLE_ADMIN;ROLE_ARTISTA"));

            // user -> ROLE_USER
            userService.add(new DTOUser(null, "user", "user123", "ROLE_ARTISTA"));




        };
    }

}
