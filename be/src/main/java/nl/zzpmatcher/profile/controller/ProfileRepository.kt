package nl.zzpmatcher.profile.controller

import nl.zzpmatcher.profile.business.Profile
import org.springframework.data.repository.CrudRepository

interface ProfileRepository : CrudRepository<Profile, String>
