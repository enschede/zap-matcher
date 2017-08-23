package nl.zzpmatcher.userlogon.business;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserRegisterCommand {

    private String emailaddress;
    private String password;
    private String password2;

    public UserRegisterCommand() {
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public User createUser() {

        final String encodedPassword = new BCryptPasswordEncoder().encode(password);

        return User.create(emailaddress, encodedPassword,"ROLE_USER");
    }
}
