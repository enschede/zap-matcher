package nl.zzpmatcher.userlogon.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private String id;
    private String emailaddress;
    private String password;
    private String roles;

    static User create(String emailaddress, String password, String roles) {
        return new User(UUID.randomUUID().toString(), emailaddress, password, roles);
    }
}
