package nl.zzpmatcher.profile.business

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Tag(@Id var id: String? = null, @ManyToOne @JsonIgnore var profile: Profile? = null)