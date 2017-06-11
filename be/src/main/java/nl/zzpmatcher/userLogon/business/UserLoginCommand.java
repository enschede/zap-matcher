package nl.zzpmatcher.userLogon.business;

import lombok.Data;

@Data
public class UserLoginCommand {
    private String username;
    private String password;
}
