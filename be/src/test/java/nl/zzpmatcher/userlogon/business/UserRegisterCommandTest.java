package nl.zzpmatcher.userlogon.business;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class UserRegisterCommandTest {

    @Test
    public void createUser() throws Exception {

        final UserRegisterCommand userRegisterCommand = new UserRegisterCommand();
        userRegisterCommand.setEmailaddress("controller@domain.com");
        userRegisterCommand.setPassword("password");

        final User user = userRegisterCommand.createUser();

        assertThat(user.getEmailaddress(), equalTo("controller@domain.com"));
        assertThat(user.getPassword().length(), equalTo(60));
    }

}