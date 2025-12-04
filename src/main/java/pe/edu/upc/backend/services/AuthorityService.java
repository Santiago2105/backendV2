package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Authority;

public interface AuthorityService {

    public Authority findById(Long id);
    public Authority findByName(String name);

    public Authority add(Authority authority);

}
