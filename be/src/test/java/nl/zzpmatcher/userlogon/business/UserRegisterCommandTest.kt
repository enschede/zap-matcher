package nl.zzpmatcher.userlogon.business

import org.junit.Test

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*

class UserRegisterCommandTest {

    @Test
    fun createUser() {

        val userRegisterCommand = UserRegisterCommand()
        userRegisterCommand.emailaddress = "controller@domain.com"
        userRegisterCommand.password = "password"

        val user = userRegisterCommand.createUser()

        assertThat<String>(user.emailaddress, equalTo("controller@domain.com"))
        assertThat(user.password!!.length, equalTo(60))
    }

}