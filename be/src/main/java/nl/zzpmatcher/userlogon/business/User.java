package nl.zzpmatcher.userlogon.business;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class User {

    @Id
    private String id;
    private String emailaddress;
    private String password;
    private String roles;

    public User() {
    }

    public User(String id, String emailaddress, String password, String roles) {
        this.id = id;
        this.emailaddress = emailaddress;
        this.password = password;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    static User create(String emailaddress, String password, String roles) {
        return new User(UUID.randomUUID().toString(), emailaddress, password, roles);
    }
}
