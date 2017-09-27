package nl.zzpmatcher.profile.business

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Profile(
    @Id var id: String? = null,
    @OneToMany(mappedBy = "profile", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true) var tags: List<Tag>? = null
)
