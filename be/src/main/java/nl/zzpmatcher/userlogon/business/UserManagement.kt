package nl.zzpmatcher.userlogon.business

import nl.zzpmatcher.utils.Validator.validate

class UserManagement(private val userRepository: UserRepository) {

    fun createUser(userRegisterCommand: UserRegisterCommand): User {
        validate(isPasswordNonEmpty(userRegisterCommand)) { throw PasswordMandatoryException() }
        validate(arePasswordsEqual(userRegisterCommand), { throw PasswordShouldBeEqual() })
        validate(isEmailAddressUnique(userRegisterCommand.emailaddress), { throw DuplicateEmailAddressException() })

        return userRepository.save(userRegisterCommand.createUser())
    }

    private fun isPasswordNonEmpty(userRegisterCommand: UserRegisterCommand): Boolean {
        return userRegisterCommand.password != null && userRegisterCommand.password!!.length > 0
    }

    private fun arePasswordsEqual(userRegisterCommand: UserRegisterCommand): Boolean {
        return userRegisterCommand.password == userRegisterCommand.password2
    }

    private fun isEmailAddressUnique(emailAddress: String?): Boolean {
        return userRepository.findUserByEmailaddress(emailAddress!!) == null
    }

    inner class DuplicateEmailAddressException internal constructor() : RuntimeException("DuplicateEmailAddressException")

    inner class PasswordMandatoryException internal constructor() : RuntimeException("PasswordMandatoryException")

    inner class PasswordShouldBeEqual internal constructor() : RuntimeException("PasswordShouldBeEqual")
}
