package nl.zzpmatcher.userlogon.business

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserRegisterCommand {

    var emailaddress: String? = null
    var password: String? = null
    var password2: String? = null

    fun createUser(): User {

        val encodedPassword = BCryptPasswordEncoder().encode(password!!)

        return User.create(emailaddress!!, encodedPassword, "ROLE_USER")
    }
}
