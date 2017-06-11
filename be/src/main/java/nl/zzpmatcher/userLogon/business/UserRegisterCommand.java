package nl.zzpmatcher.userLogon.business;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class UserRegisterCommand {

    private String emailaddress;
    private String password;
    private String password2;

    public User createUser() {

        final String encodedPassword = new BCryptPasswordEncoder().encode(password);

        return User.create(emailaddress, encodedPassword,"ROLE_USER");
    }
}
