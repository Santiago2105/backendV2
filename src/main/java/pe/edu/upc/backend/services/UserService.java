package pe.edu.upc.backend.services;


import pe.edu.upc.backend.dtos.DTOUser;
import pe.edu.upc.backend.entities.User;

public interface UserService {

    public User findById(Long id);
    public User findByUsername(String username);
    public DTOUser add(DTOUser userDTO);

}
