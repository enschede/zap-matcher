package nl.zzpmatcher.userlogon.business

import javax.persistence.Entity
import javax.persistence.Id
import java.util.UUID

@Entity
class User(
        @Id var id: String = UUID.randomUUID().toString(),
        var emailaddress: String? = null, var password: String? = null, var roles: String? = null)
