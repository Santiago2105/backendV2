package pe.edu.upc.backend.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Authority;
import pe.edu.upc.backend.repositories.AuthorityRepository;
import pe.edu.upc.backend.services.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public Authority findById(Long id) {
        return authorityRepository.findById(id).orElse(null);
    }

    @Override
    public Authority findByName(String name) {
        return authorityRepository.findByName(name);
    }

    @Override
    public Authority add(Authority authority) {
        return authorityRepository.save(authority);
    }
}
