package nl.zzpmatcher.userLogon.business;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementTest {

    @InjectMocks
    private UserManagement userManagement;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldCreateUser() throws Exception {

        final UserManagement userManagement = new UserManagement(userRepository);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.when(userRepository.save(userArgumentCaptor.capture())).thenReturn(new User());

        UserRegisterCommand userRegisterCommand =
                UserRegisterCommandBuilder.build("email@address.com", "pw1", "pw1");

        userManagement.createUser(userRegisterCommand);

        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Assert.assertThat(userArgumentCaptor.getValue().getEmailaddress(), CoreMatchers.equalTo("email@address.com"));

    }

    @Test(expected = UserManagement.PasswordShouldBeEqual.class)
    public void shouldFailOnPasswordsNotEqual() throws Exception {

        final UserManagement userManagement = new UserManagement(userRepository);

        UserRegisterCommand userRegisterCommand =
                UserRegisterCommandBuilder.build("email@address.com", "pw1", "pw2");

        userManagement.createUser(userRegisterCommand);
    }

    @Test(expected = UserManagement.PasswordMandatoryException.class)
    public void shouldFailOnPasswordEqualToNull() throws Exception {

        final UserManagement userManagement = new UserManagement(userRepository);

        UserRegisterCommand userRegisterCommand =
                UserRegisterCommandBuilder.build("email@address.com", null, null);

        userManagement.createUser(userRegisterCommand);
    }

    @Test(expected = UserManagement.DuplicateEmailAddressException.class)
    public void shouldFailOnPasswordNotUnique() throws Exception {

        final UserManagement userManagement = new UserManagement(userRepository);

        Mockito.when(userRepository.findUserByEmailaddress(Mockito.any())).thenReturn(new User());

        UserRegisterCommand userRegisterCommand =
                UserRegisterCommandBuilder.build("email@address.com", "pw1", "pw1");

        userManagement.createUser(userRegisterCommand);
    }

    public static class UserRegisterCommandBuilder {
        public static UserRegisterCommand build(String emailaddress, String password, String password2) {
            UserRegisterCommand userRegisterCommand = new UserRegisterCommand();
            userRegisterCommand.setEmailaddress(emailaddress);
            userRegisterCommand.setPassword(password);
            userRegisterCommand.setPassword2(password2);

            return userRegisterCommand;
        }
    }
}