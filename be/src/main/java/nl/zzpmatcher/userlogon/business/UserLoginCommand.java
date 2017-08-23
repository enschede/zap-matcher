package nl.zzpmatcher.userlogon.business;

import lombok.Data;

@Data
public class UserLoginCommand {
    private String username;
    private String password;
}
