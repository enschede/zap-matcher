package nl.zzpmatcher.userlogon.business

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserManagementTest {

    @InjectMocks
    private val userManagement: UserManagement? = null

    @Mock
    private val userRepository: UserRepository? = null

    @Test
    fun shouldCreateUser() {

        val userManagement = UserManagement(userRepository!!)

        val userArgumentCaptor = ArgumentCaptor.forClass(User::class.java)
        `when`(userRepository.save(userArgumentCaptor.capture())).thenReturn(User())

        val userRegisterCommand = UserRegisterCommandBuilder.build("email@address.com", "pw1", "pw1")

        userManagement.createUser(userRegisterCommand)

        verify(userRepository).save(any(User::class.java))
        Assert.assertThat<String>(userArgumentCaptor.value.emailaddress, CoreMatchers.equalTo("email@address.com"))

    }

    @Test(expected = UserManagement.PasswordShouldBeEqual::class)
    fun shouldFailOnPasswordsNotEqual() {

        val userManagement = UserManagement(userRepository!!)

        val userRegisterCommand = UserRegisterCommandBuilder.build("email@address.com", "pw1", "pw2")

        userManagement.createUser(userRegisterCommand)
    }

    @Test(expected = UserManagement.PasswordMandatoryException::class)
    fun shouldFailOnPasswordEqualToNull() {

        val userManagement = UserManagement(userRepository!!)

        val userRegisterCommand = UserRegisterCommandBuilder.build("email@address.com", null, null)

        userManagement.createUser(userRegisterCommand)
    }

    @Test(expected = UserManagement.DuplicateEmailAddressException::class)
    fun shouldFailOnPasswordNotUnique() {

        val userManagement = UserManagement(userRepository!!)

        `when`(userRepository.findUserByEmailaddress(anyString())).thenReturn(User())

        val userRegisterCommand = UserRegisterCommandBuilder.build("email@address.com", "pw1", "pw1")

        userManagement.createUser(userRegisterCommand)
    }

    object UserRegisterCommandBuilder {
        fun build(emailaddress: String, password: String?, password2: String?): UserRegisterCommand {
            val userRegisterCommand = UserRegisterCommand()
            userRegisterCommand.emailaddress = emailaddress
            userRegisterCommand.password = password
            userRegisterCommand.password2 = password2

            return userRegisterCommand
        }
    }
}