package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    public Authority findByName(String name);

}
