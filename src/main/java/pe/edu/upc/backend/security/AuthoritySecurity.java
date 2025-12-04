package pe.edu.upc.backend.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import pe.edu.upc.backend.entities.Authority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthoritySecurity implements GrantedAuthority {

    private Authority authority;

    @Override
    public String getAuthority() {
        return authority.getName();
    }


}
