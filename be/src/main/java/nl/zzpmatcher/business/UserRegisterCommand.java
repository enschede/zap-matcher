package nl.zzpmatcher.business;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserRegisterCommand {

    private String emailaddress;
    private String password;
    private String password2;

    UserRegisterCommand() {
    }

    String getEmailaddress() {
        return emailaddress;
    }

    void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    String getPassword2() {
        return password2;
    }

    void setPassword2(String password2) {
        this.password2 = password2;
    }

    User createUser() {

        final String encodedPassword = new BCryptPasswordEncoder().encode(password);

        return User.create(emailaddress, encodedPassword,"ROLE_USER");
    }
}
