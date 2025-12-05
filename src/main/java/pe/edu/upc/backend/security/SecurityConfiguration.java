package pe.edu.upc.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests((auth) -> auth
                // 1. RUTAS PÚBLICAS (Sin Login)
                .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/upc/users/login/**",
                        "/upc/users/signup/**",
                        "/upc/public/**" // <--- ¡Importante! Libera todas las rutas que empiezan con /public/
                ).permitAll()

                // 2. ARTISTAS
                // Cualquiera autenticado puede ver listas de artistas (para contratar o colaborar)
                .requestMatchers(HttpMethod.GET, "/upc/artistas/**").authenticated()
                // Solo Artista o Admin pueden modificar perfil de artista
                .requestMatchers(HttpMethod.POST, "/upc/artistas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARTISTA")
                .requestMatchers(HttpMethod.PUT, "/upc/artistas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARTISTA")
                .requestMatchers(HttpMethod.DELETE, "/upc/artistas/**").hasAnyAuthority("ROLE_ADMIN")

                // 3. RESTAURANTES
                .requestMatchers(HttpMethod.GET, "/upc/restaurantes/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/upc/restaurantes/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE")
                .requestMatchers(HttpMethod.PUT, "/upc/restaurantes/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE")
                .requestMatchers(HttpMethod.DELETE, "/upc/restaurantes/**").hasAnyAuthority("ROLE_ADMIN")

                // 4. EVENTOS y ANUNCIOS (Lógica de Negocio)
                // ¡Los Artistas deben poder ver Anuncios y Eventos para postular!
                .requestMatchers(HttpMethod.GET, "/upc/anuncios/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE", "ROLE_ARTISTA")
                .requestMatchers(HttpMethod.GET, "/upc/eventos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE", "ROLE_ARTISTA")

                // Creación y edición reservada a Restaurantes (dueños del evento) y Admin
                .requestMatchers(HttpMethod.POST, "/upc/anuncios/**", "/upc/eventos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE")
                .requestMatchers(HttpMethod.PUT, "/upc/anuncios/**", "/upc/eventos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE")
                .requestMatchers(HttpMethod.DELETE, "/upc/anuncios/**", "/upc/eventos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE")

                // 5. INTERACCIONES (Mensajes, Postulaciones)
                .requestMatchers("/upc/mensajes/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE", "ROLE_ARTISTA")
                .requestMatchers("/upc/postulaciones/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE", "ROLE_ARTISTA")

                // 6. OTROS
                .requestMatchers("/upc/resenias/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE", "ROLE_ARTISTA")
                .requestMatchers("/upc/portafolios/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE", "ROLE_ARTISTA")
                .requestMatchers("/upc/notificaciones/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE", "ROLE_ARTISTA")
                .requestMatchers("/upc/soportes/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANTE", "ROLE_ARTISTA")

                // Por defecto, todo lo demás requiere autenticación
                .anyRequest().authenticated()
        );

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}