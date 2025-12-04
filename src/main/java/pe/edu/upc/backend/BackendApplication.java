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

            // ----------------------------------------------------
            // DATOS INICIALES DRINKS N' TUNES
            // ----------------------------------------------------

            // 1) Artistas
            Artista artista1 = new Artista();
            artista1.setNombreArtistico("The Sunset Waves");
            artista1.setGeneroPrincipal("Rock");
            artista1.setBio("Banda de rock alternativo de Lima.");
            artista1.setCiudad("Lima");
            artista1 = artistaRepository.save(artista1);

            Artista artista2 = new Artista();
            artista2.setNombreArtistico("Latin Groove Band");
            artista2.setGeneroPrincipal("Salsa");
            artista2.setBio("Fusión de salsa y latin jazz.");
            artista2.setCiudad("Barranco");
            artista2 = artistaRepository.save(artista2);

            // 2) Restaurantes
            Restaurante restaurante1 = new Restaurante();
            restaurante1.setNombre("Drinks N' Tunes Bar");
            restaurante1.setDireccion("Av. Larco 123");
            restaurante1.setCiudad("Miraflores");
            restaurante1.setAforoMesas(25);
            restaurante1 = restauranteRepository.save(restaurante1);

            Restaurante restaurante2 = new Restaurante();
            restaurante2.setNombre("Acoustic Corner");
            restaurante2.setDireccion("Jr. de la Unión 456");
            restaurante2.setCiudad("Centro de Lima");
            restaurante2.setAforoMesas(15);
            restaurante2 = restauranteRepository.save(restaurante2);

            // 3) Eventos
            Evento evento1 = new Evento();
            evento1.setFechaEvento(LocalDate.of(2025, 12, 15));
            evento1.setCachet("1500 USD");
            evento1.setRealizado(false);
            evento1.setFechaCreacion(LocalDate.of(2025, 11, 1));
            evento1.setArtista(artista1);
            evento1.setRestaurante(restaurante1);
            evento1 = eventoRepository.save(evento1);

            Evento evento2 = new Evento();
            evento2.setFechaEvento(LocalDate.of(2026, 1, 10));
            evento2.setCachet("2000 USD");
            evento2.setRealizado(false);
            evento2.setFechaCreacion(LocalDate.of(2025, 11, 5));
            evento2.setArtista(artista2);
            evento2.setRestaurante(restaurante2);
            evento2 = eventoRepository.save(evento2);

            // 4) Anuncios
            Anuncio anuncio1 = new Anuncio();
            anuncio1.setTitulo("Buscamos banda de rock para show acústico");
            anuncio1.setDescripcion("Bar en Miraflores busca banda de rock para sesión acústica de 2 horas.");
            anuncio1.setGeneroBuscado("Rock");
            anuncio1.setUbicacion("Miraflores");
            anuncio1.setPresupuesto("1500");
            anuncio1.setActivo(true);
            anuncio1.setFechaCreacion(LocalDate.of(2025, 11, 2));
            anuncio1.setFechaEvento(LocalDate.of(2025, 12, 15));
            anuncio1.setEvento(evento1);
            anuncio1 = anuncioRepository.save(anuncio1);

            Anuncio anuncio2 = new Anuncio();
            anuncio2.setTitulo("Noche de salsa en vivo");
            anuncio2.setDescripcion("Restaurante en Centro de Lima busca orquesta de salsa.");
            anuncio2.setGeneroBuscado("Salsa");
            anuncio2.setUbicacion("Centro de Lima");
            anuncio2.setPresupuesto("2000");
            anuncio2.setActivo(true);
            anuncio2.setFechaCreacion(LocalDate.of(2025, 11, 3));
            anuncio2.setFechaEvento(LocalDate.of(2026, 1, 10));
            anuncio2.setEvento(evento2);
            anuncio2 = anuncioRepository.save(anuncio2);

            // 5) Mensajes
            Mensaje mensaje1 = new Mensaje();
            mensaje1.setTexto("Hola, estamos interesados en el anuncio de rock.");
            mensaje1.setFechaEnvio(LocalDate.of(2025, 11, 4));
            mensaje1.setAnuncio(anuncio1);
            // mensaje1.setUsuario(...); // si luego quieres asociar a user
            mensaje1 = mensajeRepository.save(mensaje1);

            Mensaje mensaje2 = new Mensaje();
            mensaje2.setTexto("Podemos enviar nuestro setlist para la noche de salsa.");
            mensaje2.setFechaEnvio(LocalDate.of(2025, 11, 6));
            mensaje2.setAnuncio(anuncio2);
            mensaje2 = mensajeRepository.save(mensaje2);

            // 6) Postulaciones
            Postulacion postulacion1 = new Postulacion();
            postulacion1.setMensaje("Nos gustaría tocar en su bar, tenemos repertorio acústico.");
            postulacion1.setAceptada(false);
            postulacion1.setFechaPostulacion(LocalDate.of(2025, 11, 7));
            postulacion1.setAnuncio(anuncio1);
            postulacion1.setArtista(artista1);
            postulacion1 = postulacionRepository.save(postulacion1);

            Postulacion postulacion2 = new Postulacion();
            postulacion2.setMensaje("Tenemos experiencia en eventos de salsa en vivo.");
            postulacion2.setAceptada(false);
            postulacion2.setFechaPostulacion(LocalDate.of(2025, 11, 8));
            postulacion2.setAnuncio(anuncio2);
            postulacion2.setArtista(artista2);
            postulacion2 = postulacionRepository.save(postulacion2);

            // 7) Reseñas
            Resenia resenia1 = new Resenia();
            resenia1.setPuntuacion("A");
            resenia1.setComentario("Gran presentación, el público encantado.");
            resenia1.setFechaResenia(LocalDate.of(2025, 12, 16));
            resenia1.setEvento(evento1);
            resenia1.setArtista(artista1);
            resenia1.setRestaurante(restaurante1);
            resenia1 = reseniaRepository.save(resenia1);

            Resenia resenia2 = new Resenia();
            resenia2.setPuntuacion("B");
            resenia2.setComentario("Buen ambiente, buen ritmo, se puede mejorar el sonido.");
            resenia2.setFechaResenia(LocalDate.of(2026, 1, 11));
            resenia2.setEvento(evento2);
            resenia2.setArtista(artista2);
            resenia2.setRestaurante(restaurante2);
            resenia2 = reseniaRepository.save(resenia2);

            // 8) Soporte
            Soporte soporte1 = new Soporte();
            soporte1.setAsunto("Problema al ver mis anuncios");
            soporte1.setDescripcion("No puedo ver mis anuncios publicados en el panel.");
            soporte1.setEstado(true); // abierto
            soporte1.setFechaCreacion(LocalDate.of(2025, 11, 9));
            soporte1.setFechaCierre(null);
            soporte1 = soporteRepository.save(soporte1);

            // 9) Notificaciones
            Notificacion notificacion1 = new Notificacion();
            notificacion1.setMensaje("Tu postulación ha sido recibida.");
            notificacion1.setTipoNotificacion("POSTULACION");
            notificacion1.setLeido(false);
            notificacion1.setFechaNotificacion(LocalDate.of(2025, 11, 7));
            notificacion1 = notificacionRepository.save(notificacion1);

            Notificacion notificacion2 = new Notificacion();
            notificacion2.setMensaje("Tu evento ha sido calificado.");
            notificacion2.setTipoNotificacion("RESENIA");
            notificacion2.setLeido(false);
            notificacion2.setFechaNotificacion(LocalDate.of(2025, 12, 17));
            notificacion2 = notificacionRepository.save(notificacion2);

            // 10) Portafolio
            Portafolio portafolio1 = new Portafolio();
            portafolio1.setTitulo("Live Session - Miraflores");
            portafolio1.setTipo("VIDEO");
            portafolio1.setUrl("https://youtube.com/live-sunset-waves");
            portafolio1.setFechaCreacion(LocalDate.of(2025, 10, 20));
            portafolio1.setArtista(artista1);
            portafolio1 = portafolioRepository.save(portafolio1);

            Portafolio portafolio2 = new Portafolio();
            portafolio2.setTitulo("Latin Groove - En Vivo");
            portafolio2.setTipo("AUDIO");
            portafolio2.setUrl("https://soundcloud.com/latin-groove");
            portafolio2.setFechaCreacion(LocalDate.of(2025, 9, 15));
            portafolio2.setArtista(artista2);
            portafolio2 = portafolioRepository.save(portafolio2);

            // ----------------------------------------------------
            // ADICIONES PARA PROBAR LAS QUERYS
            // ----------------------------------------------------

            // ------------------ ARTISTAS EXTRA ------------------

            Artista artista3 = new Artista();
            artista3.setNombreArtistico("Electro Vibes");
            artista3.setGeneroPrincipal("Electronica");
            artista3.setBio("DJ de música electrónica con 10 años de experiencia.");
            artista3.setCiudad("Surco");
            artista3 = artistaRepository.save(artista3);

            Artista artista4 = new Artista();
            artista4.setNombreArtistico("Acoustic Duo");
            artista4.setGeneroPrincipal("Acústico");
            artista4.setBio("Dúo acústico especializado en covers suaves.");
            artista4.setCiudad("Miraflores");
            artista4 = artistaRepository.save(artista4);


            // ------------------ RESTAURANTES EXTRA ------------------

            Restaurante restaurante3 = new Restaurante();
            restaurante3.setNombre("Vibes Lounge");
            restaurante3.setDireccion("Av. Arequipa 2981");
            restaurante3.setCiudad("San Isidro");
            restaurante3.setAforoMesas(30);
            restaurante3 = restauranteRepository.save(restaurante3);


            // ------------------ EVENTOS EXTRA ------------------

            Evento evento3 = new Evento();
            evento3.setFechaEvento(LocalDate.of(2026, 5, 20));
            evento3.setCachet("2500 USD");
            evento3.setRealizado(false);
            evento3.setFechaCreacion(LocalDate.of(2025, 12, 20));
            evento3.setArtista(artista3);
            evento3.setRestaurante(restaurante3);
            eventoRepository.save(evento3);


            // ------------------ ANUNCIOS EXTRA ------------------

            Anuncio anuncio3 = new Anuncio();
            anuncio3.setTitulo("Show Electrónico - DJ Night");
            anuncio3.setDescripcion("Vibes Lounge busca DJ para set de 3 horas.");
            anuncio3.setGeneroBuscado("Electronica");
            anuncio3.setUbicacion("San Isidro");
            anuncio3.setPresupuesto("2500");
            anuncio3.setActivo(true);
            anuncio3.setFechaCreacion(LocalDate.of(2025, 12, 22));
            anuncio3.setFechaEvento(LocalDate.of(2026, 5, 20));
            anuncio3.setEvento(evento3);
            anuncio3 = anuncioRepository.save(anuncio3);


            // ------------------ MENSAJES EXTRA ------------------

            Mensaje mensaje3 = new Mensaje();
            mensaje3.setTexto("Estoy disponible para la fecha indicada.");
            mensaje3.setFechaEnvio(LocalDate.of(2025, 12, 23));
            mensaje3.setAnuncio(anuncio3);
            mensajeRepository.save(mensaje3);


            // ------------------ POSTULACIONES EXTRA ------------------

            Postulacion postulacion3 = new Postulacion();
            postulacion3.setMensaje("Electro Vibes puede realizar un set especial de electrónica.");
            postulacion3.setAceptada(false);
            postulacion3.setFechaPostulacion(LocalDate.of(2025, 12, 23));
            postulacion3.setAnuncio(anuncio3);
            postulacion3.setArtista(artista3);
            postulacionRepository.save(postulacion3);


            // ------------------ RESENIAS EXTRA ------------------

            Resenia resenia3 = new Resenia();
            resenia3.setPuntuacion("C");
            resenia3.setComentario("Buena energía pero problemas con el sonido.");
            resenia3.setFechaResenia(LocalDate.of(2026, 5, 21));
            resenia3.setEvento(evento3);
            resenia3.setArtista(artista3);
            resenia3.setRestaurante(restaurante3);
            reseniaRepository.save(resenia3);


            // ------------------ SOPORTE EXTRA ------------------

            Soporte soporte2 = new Soporte();
            soporte2.setAsunto("No puedo ver mis postulaciones");
            soporte2.setDescripcion("El sistema no muestra mis postulaciones recientes.");
            soporte2.setEstado(true);
            soporte2.setFechaCreacion(LocalDate.of(2025, 12, 1));
            soporte2.setFechaCierre(null);
            soporteRepository.save(soporte2);


            // ------------------ NOTIFICACIONES EXTRA ------------------

            Notificacion notificacion3 = new Notificacion();
            notificacion3.setMensaje("Nuevo anuncio disponible en tu zona.");
            notificacion3.setTipoNotificacion("ANUNCIO");
            notificacion3.setLeido(false);
            notificacion3.setFechaNotificacion(LocalDate.of(2025, 12, 25));
            notificacionRepository.save(notificacion3);


            // ------------------ PORTAFOLIO EXTRA ------------------

            Portafolio portafolio3 = new Portafolio();
            portafolio3.setTitulo("DJ Set – Summer Edition");
            portafolio3.setTipo("VIDEO");
            portafolio3.setUrl("https://youtube.com/electrovibes-set");
            portafolio3.setFechaCreacion(LocalDate.of(2025, 12, 20));
            portafolio3.setArtista(artista3);
            portafolioRepository.save(portafolio3);


        };
    }

}
