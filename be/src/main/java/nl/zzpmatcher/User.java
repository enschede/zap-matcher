package nl.zzpmatcher;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class User {

    @Id
    private String id;
    private String emailaddress;
    private String password;

    public User() {
    }

    private User(String id, String emailaddress, String password) {
        this.id = id;
        this.emailaddress = emailaddress;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public static User create(String emailaddress, String password) {
        return new User(UUID.randomUUID().toString(), emailaddress, Integer.toString(password.hashCode()));
    }
}
