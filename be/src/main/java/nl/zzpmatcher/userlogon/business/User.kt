package nl.zzpmatcher.userlogon.business

import javax.persistence.Entity
import javax.persistence.Id
import java.util.UUID

@Entity
class User {

    @Id
    var id: String? = null
    var emailaddress: String? = null
    var password: String? = null
    var roles: String? = null

    constructor() {}

    constructor(id: String, emailaddress: String, password: String, roles: String) {
        this.id = id
        this.emailaddress = emailaddress
        this.password = password
        this.roles = roles
    }

    companion object {

        internal fun create(emailaddress: String, password: String, roles: String): User {
            return User(UUID.randomUUID().toString(), emailaddress, password, roles)
        }
    }
}
