package pe.edu.upc.backend.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.DTOUser;
import pe.edu.upc.backend.entities.Authority;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.repositories.UserRepository;
import pe.edu.upc.backend.services.AuthorityService;
import pe.edu.upc.backend.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    AuthorityService authorityService;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    private List<Authority> authoritiesFromString(String authorities){
        System.out.println("Autorities: "+ authorities.toString());


        List<Authority> authorityList = new ArrayList<>();
        List<String> authorityStringList = Arrays.stream(authorities.split(";")).toList();
        for(String authorityString: authorityStringList) {
            Authority authority = authorityService.findByName(authorityString);
            System.out.println("Autority: "+ authority.getId().toString());

            if (authority!=null) {
                authorityList.add(authority);
            }
        }
        return authorityList;
    }

    @Override
    public DTOUser add(DTOUser userDTO) {

        List<Authority> authorityList=authoritiesFromString(userDTO.getAuthorities());

        User newUser = new User(null, userDTO.getUsername(),
                new BCryptPasswordEncoder().encode(userDTO.getPassword()),
                true,authorityList);

        newUser = userRepository.save(newUser);
        userDTO.setId(newUser.getId());
        return userDTO;
    }


}
