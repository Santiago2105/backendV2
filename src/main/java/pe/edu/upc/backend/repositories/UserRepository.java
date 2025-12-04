package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
