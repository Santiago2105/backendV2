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

    private static final String[] AUTH_WHITELIST ={

            // -- Swagger
            "swagger-ui.html",
            "swagger-ui/**",
            "swagger-resources/**",


            // -- Login
            "upc/users/login/**",

            // -- Registro de nuevo usuarios
            "upc/users/signup/**",

    };


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*

    1. Cuales van a ser los Request(pedidos) que seran evaluados para saber si el usuario tiene permisos sobre estos request
        a. AnyRequest -> Todos los pedidos
        b. RequestMatcher -> Se evalua solo los que coincidan con las rutas especificadas
        c. RequestMatcher + HttpMethod -> Se evalua solo los que coincidan con las rutas especificadas y con el metodo Http (GET, POST, etc.)

    2. Cual es la regla de autorizacion que se va a aplicar sobre estos Request(pedidos)
        a. permitAll()
        b. denyAll()
        c. hasAnyAuthority()
        d. hasAuthority()
        e. hasRole()
        f. hasAnyRole()
        g. SpEL -> Spring Expression Language
        h. authenticated()

     */


    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(

                (auth) -> auth
//                        .anyRequest().permitAll()

                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        //PARA ARTISTAS
                        .requestMatchers(HttpMethod.GET,"/upc/artistas/**").hasAnyAuthority("ROLE_ADMIN","ROLE_ARTISTA")
                        .requestMatchers(HttpMethod.PUT,"/upc/artistas/**").hasAnyAuthority("ROLE_ADMIN","ROLE_ARTISTA")
                        .requestMatchers(HttpMethod.POST,"/upc/artistas/**").hasAnyAuthority("ROLE_ADMIN","ROLE_ARTISTA")
                        .requestMatchers(HttpMethod.DELETE,"/upc/artistas/**").hasAnyAuthority("ROLE_ADMIN","ROLE_ARTISTA")
                        //PARA RESTAURANTES
                        .requestMatchers(HttpMethod.GET,"/upc/restaurantes/**").hasAnyAuthority("ROLE_ADMIN","ROLE_RESTAURANTE")
                        .requestMatchers(HttpMethod.PUT,"/upc/restaurantes/**").hasAnyAuthority("ROLE_ADMIN","ROLE_RESTAURANTE")
                        .requestMatchers(HttpMethod.POST,"/upc/restaurantes/**").hasAnyAuthority("ROLE_ADMIN","ROLE_RESTAURANTE")
                        .requestMatchers(HttpMethod.DELETE,"/upc/restaurantes/**").hasAnyAuthority("ROLE_ADMIN","ROLE_RESTAURANTE")
                        //PARA ANUNCIOS
                        .requestMatchers(HttpMethod.GET,"/upc/anuncios/**").hasAnyAuthority("ROLE_ADMIN","ROLE_ARTISTA")
                        .requestMatchers(HttpMethod.PUT,"/upc/anuncios/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/upc/anuncios/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/upc/anuncios/**").hasAnyAuthority("ROLE_ADMIN")

                        .anyRequest().authenticated()

        );

        http.sessionManagement(
                (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

}
