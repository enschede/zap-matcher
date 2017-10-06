package nl.zzpmatcher.userlogon.business

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {

    fun findUserByEmailaddress(emailAddress: String): User

}
