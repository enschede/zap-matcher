package nl.zzpmatcher.profile.controller

import nl.zzpmatcher.profile.business.Profile
import nl.zzpmatcher.profile.business.Tag
import kotlin.streams.toList

data class ProfileProjection private constructor(val username: String?, val tags: Array<String>) {
    companion object {

        internal fun of(profile: Profile): ProfileProjection {

            val tags= profile.tags!!.stream().map { it.id!! }.toList().toTypedArray()

            return ProfileProjection(profile.id, tags)
        }
    }
}

