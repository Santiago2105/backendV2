package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.DTOToken;
import pe.edu.upc.backend.dtos.DTOUser;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.security.JwtUtilService;
import pe.edu.upc.backend.security.UserSecurity;
import pe.edu.upc.backend.services.UserService;

import java.util.stream.Collectors;

@CrossOrigin("*") //Lista de IPs que me pueden hacer peticiones
@RestController
@RequestMapping("/upc")   // http://localhost:8080/upc
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilService jwtUtilService;


    @PostMapping("/users/signup")
    public ResponseEntity<DTOUser> register(@RequestBody DTOUser user){
        user=userService.add(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }


    @PostMapping("/users/login")
    public ResponseEntity<DTOToken> login(@RequestBody User user){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
        );

        UserSecurity userSecurity = (UserSecurity) userDetailsService.loadUserByUsername(user.getUsername());

        String jwt=jwtUtilService.generateToken(userSecurity);
        Long id = userSecurity.getUser().getId();
        String authorities = userSecurity.getUser().getAuthorities().stream().
                map(authority -> authority.getName())
                .collect(Collectors.joining(";","",""));

        return new ResponseEntity<>(new DTOToken(jwt, id, authorities), HttpStatus.OK);

    }



}
